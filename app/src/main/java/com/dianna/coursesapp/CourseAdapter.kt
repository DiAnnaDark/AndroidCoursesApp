package com.dianna.coursesapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CourseAdapter(
    private val courses: List<Course>
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    class CourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val courseImage: ImageView = view.findViewById(R.id.courseImage)
        val titleText: TextView = view.findViewById(R.id.courseTitleText)
        val likeImage: ImageView = view.findViewById(R.id.likeImage)
        val descriptionText: TextView = view.findViewById(R.id.courseDescriptionText)
        val startDateText: TextView = view.findViewById(R.id.courseStartDateText)
        val priceText: TextView = view.findViewById(R.id.coursePriceText)
        val rateText: TextView = view.findViewById(R.id.courseRateText)
        val detailsText: TextView = view.findViewById(R.id.detailsText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_course, parent, false)

        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]

        holder.titleText.text = course.title
        holder.descriptionText.text = course.text
        holder.startDateText.text = formatDate(course.startDate)
        holder.priceText.text = "${course.price} ₽"
        holder.rateText.text = "★ ${course.rate}"
        holder.detailsText.text = "Подробнее →"

        holder.courseImage.setImageResource(
            when (course.id) {
                100 -> R.drawable.course_java
                101 -> R.drawable.course_3d
                102 -> R.drawable.course_python
                103 -> R.drawable.course_analytics
                104 -> R.drawable.course_data
                else -> R.drawable.course_java
            }
        )

        updateLikeIcon(holder, course)

        holder.likeImage.setOnClickListener {
            course.hasLike = !course.hasLike
            updateLikeIcon(holder, course)

            CoroutineScope(Dispatchers.IO).launch {
                val dao = DatabaseProvider
                    .getDatabase(holder.itemView.context)
                    .favoriteCourseDao()

                if (course.hasLike) {
                    dao.insertFavorite(course.toFavoriteEntity())
                } else {
                    dao.deleteFavorite(course.toFavoriteEntity())
                }
            }
        }

        holder.detailsText.setOnClickListener {
            val context = holder.itemView.context

            val intent = Intent(context, CourseDetailsActivity::class.java)
            intent.putExtra("id", course.id)
            intent.putExtra("title", course.title)
            intent.putExtra("text", course.text)
            intent.putExtra("price", course.price)
            intent.putExtra("rate", course.rate)
            intent.putExtra("startDate", course.startDate)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = courses.size

    private fun updateLikeIcon(holder: CourseViewHolder, course: Course) {
        holder.likeImage.setImageResource(
            if (course.hasLike) {
                R.drawable.ic_bookmark_filled
            } else {
                R.drawable.ic_bookmark_outline
            }
        )

        holder.likeImage.setColorFilter(
            if (course.hasLike) {
                android.graphics.Color.parseColor("#55C267")
            } else {
                android.graphics.Color.WHITE
            }
        )
    }

    private fun formatDate(date: String): String {
        val parts = date.split("-")

        if (parts.size != 3) {
            return date
        }

        val year = parts[0]
        val month = when (parts[1]) {
            "01" -> "Января"
            "02" -> "Февраля"
            "03" -> "Марта"
            "04" -> "Апреля"
            "05" -> "Мая"
            "06" -> "Июня"
            "07" -> "Июля"
            "08" -> "Августа"
            "09" -> "Сентября"
            "10" -> "Октября"
            "11" -> "Ноября"
            "12" -> "Декабря"
            else -> parts[1]
        }
        val day = parts[2]

        return "$day $month $year"
    }

    private fun Course.toFavoriteEntity(): FavoriteCourseEntity {
        return FavoriteCourseEntity(
            id = id,
            title = title,
            text = text,
            price = price,
            rate = rate,
            startDate = startDate,
            publishDate = publishDate
        )
    }
}