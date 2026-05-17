package com.dianna.coursesapp

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class CourseDetailsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_details)

        val titleText = findViewById<TextView>(R.id.courseTitleText)
        val descriptionText = findViewById<TextView>(R.id.courseDescriptionText)
        val startDateText = findViewById<TextView>(R.id.courseStartDateText)
        val rateText = findViewById<TextView>(R.id.courseRateText)
        val priceText = findViewById<TextView>(R.id.coursePriceText)

        val title = intent.getStringExtra("title")
        val text = intent.getStringExtra("text")
        val price = intent.getStringExtra("price")
        val rate = intent.getStringExtra("rate")
        val startDate = intent.getStringExtra("startDate")

        titleText.text = title
        descriptionText.text = text
        startDateText.text = "Старт: $startDate"
        rateText.text = "★ $rate"
        priceText.text = "$price ₽"
    }
}