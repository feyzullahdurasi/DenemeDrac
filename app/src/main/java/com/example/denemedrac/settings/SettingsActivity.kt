package com.example.denemedrac.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.example.denemedrac.R

class SettingsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userNameTextView: TextView
    private lateinit var editUserNameButton: Button
    private lateinit var logOutButton: Button
    private lateinit var notificationsSwitch: Switch
    private lateinit var themeButton: Button
    private lateinit var speedUnitSpinner: Spinner
    private lateinit var streamingServiceSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        sharedPreferences = getSharedPreferences("app_settings", MODE_PRIVATE)

        // Initialize UI components
        userNameTextView = findViewById(R.id.user_name_text_view)
        editUserNameButton = findViewById(R.id.edit_user_name_button)
        logOutButton = findViewById(R.id.log_out_button)
        notificationsSwitch = findViewById(R.id.notifications_switch)
        themeButton = findViewById(R.id.theme_button)
        speedUnitSpinner = findViewById(R.id.speed_unit_spinner)
        streamingServiceSpinner = findViewById(R.id.streaming_service_spinner)

        // Set up initial values from SharedPreferences
        setupUI()

        // Set up event listeners
        editUserNameButton.setOnClickListener { editUserName() }
        logOutButton.setOnClickListener { logOut() }
        notificationsSwitch.setOnCheckedChangeListener { _, isChecked -> toggleNotifications(isChecked) }
        themeButton.setOnClickListener { openThemeChangeDialog() }
    }

    private fun setupUI() {
        // Get saved username or set default
        val userName = sharedPreferences.getString("userName", "User Name")
        userNameTextView.text = userName

        // Set notifications switch state
        notificationsSwitch.isChecked = sharedPreferences.getBoolean("areNotificationsEnabled", false)

        // Set up the spinner for speed unit (km/s or mph)
        /*ArrayAdapter.createFromResource(
            this,
            R.array.speed_unit_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            speedUnitSpinner.adapter = adapter
        }*/
        val savedSpeedUnitIndex = sharedPreferences.getInt("speedUnitIndex", 0)
        speedUnitSpinner.setSelection(savedSpeedUnitIndex)

        speedUnitSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                sharedPreferences.edit { putInt("speedUnitIndex", position) }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Set up the spinner for streaming services (YouTube Music, Spotify, Apple Music)
        /*ArrayAdapter.createFromResource(
            this,
            R.array.streaming_services_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            streamingServiceSpinner.adapter = adapter
        }*/
        val savedStreamingService = sharedPreferences.getString("preferredStreamingService", "Spotify")
        val streamingServiceIndex = when (savedStreamingService) {
            "YouTube Music" -> 0
            "Spotify" -> 1
            "Apple Music" -> 2
            else -> 0
        }
        streamingServiceSpinner.setSelection(streamingServiceIndex)

        streamingServiceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedService = when (position) {
                    0 -> "YouTube Music"
                    1 -> "Spotify"
                    2 -> "Apple Music"
                    else -> "Spotify"
                }
                sharedPreferences.edit { putString("preferredStreamingService", selectedService) }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun editUserName() {
        val editText = EditText(this)
        editText.setText(userNameTextView.text)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Edit User Name")
            .setView(editText)
            .setPositiveButton("Save") { _, _ ->
                val newUserName = editText.text.toString().trim() // Ensure it's trimmed
                if (newUserName.isNotEmpty()) { // Check for empty input
                    userNameTextView.text = newUserName
                    sharedPreferences.edit { putString("userName", newUserName) }
                } else {
                    Toast.makeText(this, "User name cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun logOut() {
        sharedPreferences.edit {
            remove("userName")
        }
        userNameTextView.text = "" // Reset username view
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
    }

    private fun toggleNotifications(isEnabled: Boolean) {
        sharedPreferences.edit {
            putBoolean("areNotificationsEnabled", isEnabled)
        }
    }

    private fun openThemeChangeDialog() {
        val themes = arrayOf("System Default", "Light", "Dark")
        val selectedThemeIndex = when (sharedPreferences.getString("userTheme", "default")) {
            "light" -> 1
            "dark" -> 2
            else -> 0
        }

        AlertDialog.Builder(this)
            .setTitle("Choose Theme")
            .setSingleChoiceItems(themes, selectedThemeIndex) { dialog, which ->
                val selectedTheme = when (which) {
                    1 -> AppCompatDelegate.MODE_NIGHT_NO
                    2 -> AppCompatDelegate.MODE_NIGHT_YES
                    else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }
                AppCompatDelegate.setDefaultNightMode(selectedTheme)

                val themeValue = when (which) {
                    1 -> "light"
                    2 -> "dark"
                    else -> "default"
                }
                sharedPreferences.edit { putString("userTheme", themeValue) }
                dialog.dismiss()
            }
            .create()
            .show()
    }
}
