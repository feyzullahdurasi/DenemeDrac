package com.example.denemedrac.begin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.denemedrac.R
import com.example.denemedrac.main.MainActivity

class OnboardingActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var errorTextView: TextView
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE)

        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        errorTextView = findViewById(R.id.errorTextView)
        signUpButton = findViewById(R.id.signUpButton)

        signUpButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()

            if (name.isEmpty() || email.isEmpty()) {
                errorTextView.text = "Both fields are required."
                errorTextView.setTextColor(resources.getColor(android.R.color.holo_red_dark))
            } else {
                errorTextView.text = ""
                saveUserInfo(name, email)
            }
        }
    }

    private fun saveUserInfo(name: String, email: String) {
        val editor = sharedPreferences.edit()
        editor.putString("userName", name)
        editor.putString("userEmail", email)
        editor.putBoolean("isUserInfoSaved", true)
        editor.apply()

        Toast.makeText(this, "User info saved", Toast.LENGTH_SHORT).show()

        // Start MainActivity after saving user info
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Close OnboardingActivity
    }
}
