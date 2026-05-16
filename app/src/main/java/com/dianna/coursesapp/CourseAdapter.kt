package com.dianna.coursesapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val likeText: TextView = view.findViewById(R.id.likeText)
        val descriptionText: TextView = view.findViewById(R.id.courseDescriptionText)
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
        holder.priceText.text = "${course.price} ₽"
        holder.rateText.text = "★ ${course.rate}"
        holder.detailsText.text = "Подробнее →"

        updateLikeVisibility(holder, course)

        holder.likeText.setOnClickListener {
            course.hasLike = !course.hasLike
            updateLikeVisibility(holder, course)

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
    }

    override fun getItemCount(): Int = courses.size

    private fun updateLikeVisibility(holder: CourseViewHolder, course: Course) {
        holder.likeText.visibility = View.VISIBLE
        holder.likeText.text = "\u2764\uFE0E"

        holder.likeText.setTextColor(
            if (course.hasLike) {
                Color.parseColor("#FF5E7A")
            } else {
                Color.parseColor("#6F7078")
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