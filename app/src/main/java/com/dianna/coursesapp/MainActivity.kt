package com.dianna.coursesapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText

class MainActivity : Activity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var vkButton: Button
    private lateinit var okButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        vkButton = findViewById(R.id.vkButton)
        okButton = findViewById(R.id.okButton)

        loginButton.isEnabled = false

        emailEditText.addTextChangedListener(inputWatcher)
        passwordEditText.addTextChangedListener(inputWatcher)

        loginButton.setOnClickListener {
            val intent = Intent(this, CoursesActivity::class.java)
            startActivity(intent)
        }

        vkButton.setOnClickListener {
            openBrowser("https://vk.com/")
        }

        okButton.setOnClickListener {
            openBrowser("https://ok.ru/")
        }
    }

    private val inputWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateInput()
        }

        override fun afterTextChanged(s: Editable?) = Unit
    }

    private fun validateInput() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString()

        loginButton.isEnabled = isValidEmail(email) && password.isNotBlank()
    }

    private fun isValidEmail(email: String): Boolean {
        val hasCyrillic = email.any { it in 'а'..'я' || it in 'А'..'Я' || it == 'ё' || it == 'Ё' }
        val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

        return !hasCyrillic && emailRegex.matches(email)
    }

    private fun openBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}