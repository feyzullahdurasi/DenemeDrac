package com.example.denemedrac.begin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.denemedrac.R
import com.example.denemedrac.main.MainActivity

class WelcomeMessageActivity : AppCompatActivity() {

    private lateinit var welcomeTextView: TextView
    private lateinit var rootView: ConstraintLayout
    private var userName: String = "Feyzullah Durası" // Example username

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_message)

        rootView = findViewById(R.id.rootView)
        welcomeTextView = findViewById(R.id.welcomeTextView)

        // Set username in the welcome message
        welcomeTextView.text = "Hello, $userName!"

        // Show the welcome message
        showWelcomeMessage()

        // Delay for 2 seconds and then switch to MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            // Start MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // Finish WelcomeMessageActivity so it doesn't show again when pressing back
            finish()
        }, 2000) // 2000 milliseconds = 2 seconds
    }

    private fun showWelcomeMessage() {
        welcomeTextView.visibility = View.VISIBLE
    }
}
