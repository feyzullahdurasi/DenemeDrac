package com.example.denemedrac.music

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.denemedrac.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MusicViewActivity : AppCompatActivity() {

    private lateinit var musicViewPager: ViewPager2
    private lateinit var musicTabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_view)

        musicViewPager = findViewById(R.id.music_view_pager)
        musicTabLayout = findViewById(R.id.music_tabs)

        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter = MusicPagerAdapter(this)
        musicViewPager.adapter = adapter

        TabLayoutMediator(musicTabLayout, musicViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Spotify"
                1 -> "YouTube Music"
                2 -> "Radio"
                else -> ""
            }
        }.attach()

        // Set default selected tab based on intent
        val selectedService = intent.getStringExtra("selectedStreamingService") ?: "Spotify"
        val tabPosition = when (selectedService) {
            "Spotify" -> 0
            "YouTube Music" -> 1
            "Radio" -> 2
            else -> 0
        }
        musicViewPager.currentItem = tabPosition
    }
}