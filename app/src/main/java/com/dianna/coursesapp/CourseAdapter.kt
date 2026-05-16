package com.dianna.coursesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

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
        holder.priceText.text = course.price
        holder.rateText.text = "★ ${course.rate}"
        holder.detailsText.text = "Подробнее →"

        holder.likeText.visibility =
            if (course.hasLike) View.VISIBLE else View.GONE
    }

    override fun getItemCount(): Int {
        return courses.size
    }
}