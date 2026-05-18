package com.dianna.coursesapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoursesActivity : Activity() {

    private lateinit var coursesRecyclerView: RecyclerView
    private lateinit var adapter: CourseAdapter
    private var courses: List<Course> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courses)

        coursesRecyclerView = findViewById(R.id.coursesRecyclerView)

        val sortText = findViewById<TextView>(R.id.sortText)
        val favoritesMenuText = findViewById<LinearLayout>(R.id.favoritesMenuText)
        val profileMenuText = findViewById<LinearLayout>(R.id.profileMenuText)

        coursesRecyclerView.layoutManager = LinearLayoutManager(this)

        loadCourses()

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

    private fun loadCourses() {
        CoroutineScope(Dispatchers.Main).launch {
            courses = try {
                withContext(Dispatchers.IO) {
                    RetrofitClient.api.getCourses().courses
                }
            } catch (exception: Exception) {
                CoursesRepository.getCourses()
            }

            adapter = CourseAdapter(courses)
            coursesRecyclerView.adapter = adapter
        }
    }
}