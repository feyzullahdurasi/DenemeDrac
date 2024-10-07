package com.example.denemedrac.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.denemedrac.R
import com.example.denemedrac.begin.OnboardingActivity
import com.example.denemedrac.music.MusicViewActivity
import com.example.denemedrac.settings.SettingsActivity
import com.example.denemedrac.shopping.ShoppingViewActivity
import com.example.denemedrac.sleep.SleepModeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var buttonBar: View
    private lateinit var showButtonBarButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        buttonBar = findViewById(R.id.button_bar)
        showButtonBarButton = findViewById(R.id.hide_button_bar)

        setupButtonBar()
        setupActiveViews()

        if (isFirstLaunch()) {
            startActivity(Intent(this, OnboardingActivity::class.java))
        } else {
            showWelcomeMessage()
        }
    }

    private fun setupButtonBar() {
        findViewById<Button>(R.id.btn_sleep_mode).setOnClickListener {
            startActivity(Intent(this, SleepModeActivity::class.java))
        }
        findViewById<Button>(R.id.btn_shopping).setOnClickListener {
            startActivity(Intent(this, ShoppingViewActivity::class.java))
        }
        findViewById<Button>(R.id.btn_settings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        findViewById<Button>(R.id.btn_music).setOnClickListener {
            startActivity(Intent(this, MusicViewActivity::class.java).apply {
                putExtra("selectedStreamingService", "Spotify") // Default service or fetch from user preferences
            })
        }
        findViewById<Button>(R.id.hide_button_bar).setOnClickListener {
            toggleButtonBar()
        }
        showButtonBarButton.setOnClickListener {
            toggleButtonBar()
        }

        ActiveView.values().forEach { view ->
            findViewById<Button>(getButtonId(view)).setOnClickListener {
                viewModel.toggleView(view)
            }
        }
    }

    private fun setupActiveViews() {
        viewModel.activeViews.observe(this) { activeViews ->
            ActiveView.values().forEach { view ->
                findViewById<View>(getViewId(view)).visibility =
                    if (view in activeViews) View.VISIBLE else View.GONE
            }
        }
    }

    private fun toggleButtonBar() {
        if (buttonBar.visibility == View.VISIBLE) {
            buttonBar.visibility = View.GONE
            showButtonBarButton.visibility = View.VISIBLE
        } else {
            buttonBar.visibility = View.VISIBLE
            showButtonBarButton.visibility = View.GONE
        }
    }

    private fun isFirstLaunch(): Boolean {
        // Implement this method based on your needs
        return false
    }

    private fun showWelcomeMessage() {
        // Implement this method to show a welcome message
    }

    private fun getButtonId(view: ActiveView): Int {
        return when (view) {
            ActiveView.MAPS -> R.id.btn_maps
            ActiveView.CONTACTS -> R.id.btn_contacts
            ActiveView.MUSIC -> R.id.btn_music
            ActiveView.SPEEDOMETER -> R.id.btn_speedometer
            ActiveView.SPEED -> R.id.btn_speed
        }
    }

    private fun getViewId(view: ActiveView): Int {
        return when (view) {
            ActiveView.MAPS -> R.id.map_view
            ActiveView.CONTACTS -> R.id.contacts_view
            ActiveView.MUSIC -> R.id.music_view
            ActiveView.SPEEDOMETER -> R.id.speedometer_view
            ActiveView.SPEED -> R.id.speed_view
        }
    }
}