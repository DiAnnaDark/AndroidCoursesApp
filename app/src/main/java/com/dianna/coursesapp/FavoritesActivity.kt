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

class FavoritesActivity : Activity() {

    private lateinit var favoritesRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        favoritesRecyclerView = findViewById(R.id.favoritesRecyclerView)

        val homeMenuText =
            findViewById<LinearLayout>(R.id.homeMenuText)

        val profileMenuText =
            findViewById<LinearLayout>(R.id.profileMenuText)

        favoritesRecyclerView.layoutManager =
            LinearLayoutManager(this)

        loadFavorites()

        homeMenuText.setOnClickListener {
            val intent = Intent(this, CoursesActivity::class.java)
            startActivity(intent)
        }

        profileMenuText.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadFavorites()
    }

    private fun loadFavorites() {
        CoroutineScope(Dispatchers.Main).launch {
            val favoriteCourses = withContext(Dispatchers.IO) {
                DatabaseProvider
                    .getDatabase(this@FavoritesActivity)
                    .favoriteCourseDao()
                    .getAllFavorites()
                    .map { it.toCourse() }
            }

            favoritesRecyclerView.adapter =
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