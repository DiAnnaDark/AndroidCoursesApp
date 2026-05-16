package com.dianna.coursesapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoritesActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        val favoritesRecyclerView =
            findViewById<RecyclerView>(R.id.favoritesRecyclerView)

        val homeMenuText =
            findViewById<TextView>(R.id.homeMenuText)

        val profileMenuText =
            findViewById<TextView>(R.id.profileMenuText)

        val favoriteCourses =
            CoursesRepository.getFavoriteCourses()

        favoritesRecyclerView.layoutManager =
            LinearLayoutManager(this)

        favoritesRecyclerView.adapter =
            CourseAdapter(favoriteCourses)

        homeMenuText.setOnClickListener {
            val intent = Intent(this, CoursesActivity::class.java)
            startActivity(intent)
        }

        profileMenuText.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}