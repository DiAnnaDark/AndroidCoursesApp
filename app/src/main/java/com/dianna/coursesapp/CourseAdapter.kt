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
        holder.startDateText.text = "Старт: ${course.startDate}"
        holder.priceText.text = "${course.price} ₽"
        holder.rateText.text = "★ ${course.rate}"
        holder.detailsText.text = "Подробнее →"

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