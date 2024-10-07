package com.example.denemedrac.speed

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.denemedrac.R
import java.util.*

class SpeedometerActivity : AppCompatActivity() {

    private lateinit var speedTextView: TextView
    private lateinit var rpmTextView: TextView
    private var speed = 0.0
    private var rpm = 0.0
    private val handler = Handler(Looper.getMainLooper())
    private val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speedometer)

        speedTextView = findViewById(R.id.speedTextView)
        rpmTextView = findViewById(R.id.rpmTextView)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        startUpdatingValues()
    }

    private fun startUpdatingValues() {
        val task = object : TimerTask() {
            override fun run() {
                handler.post {
                    speed = 230.0 // Simulate speed value
                    rpm = 5840.0  // Simulate RPM value
                    updateSpeedometer()
                }
            }
        }
        timer.scheduleAtFixedRate(task, 0, 100) // Update every 0.1 seconds
    }

    private fun updateSpeedometer() {
        speedTextView.text = String.format("%.0f km/h", speed)
        rpmTextView.text = String.format("%.0f RPM", rpm / 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR // Reset orientation lock
    }
}
