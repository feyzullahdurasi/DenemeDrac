package com.example.denemedrac.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.denemedrac.R
import com.example.denemedrac.music.MusicViewActivity
import com.example.denemedrac.settings.SettingsActivity
import com.example.denemedrac.shopping.ShoppingViewActivity
import com.example.denemedrac.sleep.SleepModeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var buttonBar: LinearLayout
    private lateinit var hideButtonBar: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mainContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mainContainer = findViewById(R.id.main_container)
        buttonBar = findViewById(R.id.button_bar)
        hideButtonBar = findViewById(R.id.hide_button_bar)

        setupButtonBar()
        setupActiveViews()
        updateLayoutForOrientation(resources.configuration.orientation)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateLayoutForOrientation(newConfig.orientation)
    }

    private fun updateLayoutForOrientation(orientation: Int) {
        // Her iki yönelimde de button bar'ı horizontal tut
        buttonBar.orientation = LinearLayout.HORIZONTAL

        // Button bar'ın bottom'da kalmasını sağla
        val params = buttonBar.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.BOTTOM
        buttonBar.layoutParams = params
    }

    private fun setupButtonBar() {
        // Butonların tıklama olaylarını ayarla
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
                putExtra("selectedStreamingService", "Spotify")
            })
        }

        // Hide button'un tıklama olayı
        hideButtonBar.setOnClickListener {
            toggleButtonBar()
        }

        // Aktif view'ları toggle etme
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
        val isVisible = buttonBar.visibility == View.VISIBLE
        val hideButton = findViewById<Button>(R.id.hide_button_bar)

        // Translate animasyonu
        val buttonBarTranslate = if (isVisible) {
            ObjectAnimator.ofFloat(buttonBar, "translationY", 0f, buttonBar.height.toFloat())
        } else {
            ObjectAnimator.ofFloat(buttonBar, "translationY", buttonBar.height.toFloat(), 0f)
        }

        // Rotate animasyonu
        val rotateAnimator = ObjectAnimator.ofFloat(
            hideButton,
            "rotation",
            if (isVisible) 0f else 180f
        )

        buttonBarTranslate.duration = 300
        rotateAnimator.duration = 300

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(buttonBarTranslate, rotateAnimator)

        animatorSet.start()

        // Görünürlüğü değiştir
        buttonBar.visibility = if (isVisible) View.GONE else View.VISIBLE
        hideButton.text = if (isVisible) "Show" else "Hide"
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