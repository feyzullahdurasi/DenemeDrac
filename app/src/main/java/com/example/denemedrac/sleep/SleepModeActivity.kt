package com.example.denemedrac.sleep

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.denemedrac.R

class SleepModeActivity : AppCompatActivity() {

    private lateinit var clockView: AnalogClockView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleep_mode)

        clockView = findViewById(R.id.analogClockView)

        // Setup back button to exit sleep mode
        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish() // Close activity
        }
    }
}