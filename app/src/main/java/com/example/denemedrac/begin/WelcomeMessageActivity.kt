package com.example.denemedrac.begin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.denemedrac.R

class WelcomeMessageActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var welcomeTextView: TextView
    private lateinit var rootView: ConstraintLayout
    private var isVisible: Boolean = true // Set default visibility
    private var userName: String = "Feyzullah Durası" // Example username
    private var userTheme: Int = R.style.Theme_App_Default // Default theme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("userPreferences", Context.MODE_PRIVATE)

        // Load saved theme
        userTheme = sharedPreferences.getInt("userTheme", R.style.Theme_App_Default)
        setTheme(userTheme)

        setContentView(R.layout.activity_welcome_message)

        rootView = findViewById(R.id.rootView)
        welcomeTextView = findViewById(R.id.welcomeTextView)

        // Set username in the welcome message
        welcomeTextView.text = "Hello, $userName!"

        // Set initial visibility of the text
        welcomeTextView.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE

        // Apply animation for visibility
        if (isVisible) {
            showWelcomeMessage()
        } else {
            hideWelcomeMessage()
        }

        // Toggle theme when clicked (as an example of theme changing)
        rootView.setOnClickListener {
            toggleTheme()
        }
    }

    private fun showWelcomeMessage() {
        welcomeTextView.visibility = View.VISIBLE
        val animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = 500 // 0.5 seconds
        welcomeTextView.startAnimation(animation)
    }

    private fun hideWelcomeMessage() {
        val animation = AlphaAnimation(1.0f, 0.0f)
        animation.duration = 500 // 0.5 seconds
        welcomeTextView.startAnimation(animation)
        welcomeTextView.visibility = View.INVISIBLE
    }

    private fun toggleTheme() {
        // Example theme toggling logic, you can define more themes
        userTheme = if (userTheme == R.style.Theme_App_Default) {
            R.style.Theme_App_Dark
        } else {
            R.style.Theme_App_Default
        }
        // Save the selected theme
        val editor = sharedPreferences.edit()
        editor.putInt("userTheme", userTheme)
        editor.apply()

        // Restart the activity to apply the theme
        recreate()
    }
}
