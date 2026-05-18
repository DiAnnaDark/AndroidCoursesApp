package com.dianna.coursesapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CourseDetailsActivity : Activity() {

    private var hasLike = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_details)

        val courseImage = findViewById<ImageView>(R.id.courseImage)
        val backButton = findViewById<ImageButton>(R.id.backButton)
        val bookmarkImage = findViewById<ImageView>(R.id.bookmarkImage)

        val titleText = findViewById<TextView>(R.id.courseTitleText)
        val descriptionText = findViewById<TextView>(R.id.courseDescriptionText)

        val imageRateText = findViewById<TextView>(R.id.imageRateText)
        val imageDateText = findViewById<TextView>(R.id.imageDateText)

        val homeMenuText = findViewById<LinearLayout>(R.id.homeMenuText)
        val favoritesMenuText = findViewById<LinearLayout>(R.id.favoritesMenuText)
        val profileMenuText = findViewById<LinearLayout>(R.id.profileMenuText)

        val id = intent.getIntExtra("id", 100)
        val title = intent.getStringExtra("title") ?: ""
        val text = intent.getStringExtra("text") ?: ""
        val price = intent.getStringExtra("price") ?: ""
        val rate = intent.getStringExtra("rate") ?: ""
        val startDate = intent.getStringExtra("startDate") ?: ""
        val publishDate = intent.getStringExtra("publishDate") ?: startDate

        val favoriteCourse = FavoriteCourseEntity(
            id = id,
            title = title,
            text = text,
            price = price,
            rate = rate,
            startDate = startDate,
            publishDate = publishDate
        )

        courseImage.setImageResource(getCourseImage(id))

        titleText.text = title
        descriptionText.text = text
        imageRateText.text = "★ $rate"
        imageDateText.text = startDate

        loadBookmarkState(id, bookmarkImage)

        bookmarkImage.setOnClickListener {
            hasLike = !hasLike
            updateBookmarkIcon(bookmarkImage)

            CoroutineScope(Dispatchers.IO).launch {
                val dao = DatabaseProvider
                    .getDatabase(this@CourseDetailsActivity)
                    .favoriteCourseDao()

                if (hasLike) {
                    dao.insertFavorite(favoriteCourse)
                } else {
                    dao.deleteFavorite(favoriteCourse)
                }
            }
        }

        backButton.setOnClickListener {
            finish()
        }

        homeMenuText.setOnClickListener {
            startActivity(Intent(this, CoursesActivity::class.java))
        }

        favoritesMenuText.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }

        profileMenuText.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun loadBookmarkState(courseId: Int, bookmarkImage: ImageView) {
        CoroutineScope(Dispatchers.Main).launch {
            hasLike = withContext(Dispatchers.IO) {
                DatabaseProvider
                    .getDatabase(this@CourseDetailsActivity)
                    .favoriteCourseDao()
                    .getAllFavorites()
                    .any { it.id == courseId }
            }

            updateBookmarkIcon(bookmarkImage)
        }
    }

    private fun updateBookmarkIcon(bookmarkImage: ImageView) {
        bookmarkImage.setImageResource(
            if (hasLike) {
                R.drawable.ic_bookmark_filled
            } else {
                R.drawable.ic_bookmark_outline
            }
        )
    }

    private fun getCourseImage(courseId: Int): Int {
        return when (courseId) {
            100 -> R.drawable.course_java
            101 -> R.drawable.course_3d
            102 -> R.drawable.course_python
            103 -> R.drawable.course_analytics
            104 -> R.drawable.course_data
            else -> R.drawable.course_java
        }
    }
}