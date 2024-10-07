package com.example.denemedrac.speed

import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat
import com.example.denemedrac.R
import java.util.*
import kotlin.concurrent.timerTask

class SpeedViewActivity : AppCompatActivity() {

    private lateinit var speedTextView: TextView
    private lateinit var startButton: Button
    private lateinit var countdownTextView: TextView
    private lateinit var elapsedTimeTextView: TextView
    private var speed = 0.0
    private var timer: Timer? = null
    private var countdownTimer: Timer? = null
    private var secondsRemaining = 5
    private var startTime: Long = 0
    private var isCountdownFinished = false
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speed_view)

        speedTextView = findViewById(R.id.speedTextView)
        startButton = findViewById(R.id.startButton)
        countdownTextView = findViewById(R.id.countdownTextView)
        elapsedTimeTextView = findViewById(R.id.elapsedTimeTextView)

        startButton.setOnClickListener {
            startCountdown()
        }
    }

    private fun startCountdown() {
        secondsRemaining = 5
        isCountdownFinished = false
        countdownTimer?.cancel()
        countdownTimer = Timer()
        startButton.isEnabled = false

        countdownTimer?.scheduleAtFixedRate(timerTask {
            runOnUiThread {
                if (secondsRemaining > 0) {
                    countdownTextView.text = secondsRemaining.toString()
                    secondsRemaining--
                } else {
                    countdownTextView.text = "Go!"
                    isCountdownFinished = true
                    startElapsedTime()
                    countdownTimer?.cancel()
                }
            }
        }, 0, 1000)
    }

    private fun startElapsedTime() {
        startTime = System.currentTimeMillis()
        timer?.cancel()
        timer = Timer()
        timer?.scheduleAtFixedRate(timerTask {
            val elapsedTime = (System.currentTimeMillis() - startTime) / 1000.0
            runOnUiThread {
                elapsedTimeTextView.text = String.format("%.2f sec", elapsedTime)
            }
        }, 0, 10)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
        countdownTimer?.cancel()
    }
}
