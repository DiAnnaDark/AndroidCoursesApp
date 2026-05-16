package com.dianna.coursesapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CoursesActivity : Activity() {

    private lateinit var coursesRecyclerView: RecyclerView
    private lateinit var adapter: CourseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courses)

        coursesRecyclerView = findViewById(R.id.coursesRecyclerView)

        val sortText = findViewById<TextView>(R.id.sortText)
        val favoritesMenuText = findViewById<TextView>(R.id.favoritesMenuText)
        val profileMenuText = findViewById<TextView>(R.id.profileMenuText)

        val courses = CoursesRepository.getCourses().toMutableList()

        adapter = CourseAdapter(courses)

        coursesRecyclerView.layoutManager = LinearLayoutManager(this)
        coursesRecyclerView.adapter = adapter

        sortText.setOnClickListener {
            val sortedCourses = courses.sortedByDescending { it.publishDate }
            adapter = CourseAdapter(sortedCourses)
            coursesRecyclerView.adapter = adapter
        }

        favoritesMenuText.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }

        profileMenuText.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}