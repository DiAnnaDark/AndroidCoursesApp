package com.dianna.coursesapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : Activity() {

    private lateinit var profileCoursesRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profileCoursesRecyclerView =
            findViewById(R.id.profileCoursesRecyclerView)

        val homeMenuText =
            findViewById<LinearLayout>(R.id.homeMenuText)

        val favoritesMenuText =
            findViewById<LinearLayout>(R.id.favoritesMenuText)

        profileCoursesRecyclerView.layoutManager =
            LinearLayoutManager(this)

        loadFavoriteCourses()

        homeMenuText.setOnClickListener {
            startActivity(Intent(this, CoursesActivity::class.java))
        }

        favoritesMenuText.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadFavoriteCourses()
    }

    private fun loadFavoriteCourses() {
        CoroutineScope(Dispatchers.Main).launch {

            val favoriteCourses = withContext(Dispatchers.IO) {
                DatabaseProvider
                    .getDatabase(this@ProfileActivity)
                    .favoriteCourseDao()
                    .getAllFavorites()
                    .map { it.toCourse() }
            }

            profileCoursesRecyclerView.adapter =
                CourseAdapter(favoriteCourses)
        }
    }

    private fun FavoriteCourseEntity.toCourse(): Course {
        return Course(
            id = id,
            title = title,
            text = text,
            price = price,
            rate = rate,
            startDate = startDate,
            hasLike = true,
            publishDate = publishDate
        )
    }
}