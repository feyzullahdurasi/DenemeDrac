package com.example.denemedrac.music

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.denemedrac.R
import com.example.denemedrac.music.radio.RadioFragment
import com.example.denemedrac.music.spotify.SpotifyFragment
import com.example.denemedrac.music.youtubeMusic.YoutubeMusicFragment


class MusicViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_view)

        // Get the selected streaming service from the intent or preferences
        val selectedStreamingService = intent.getStringExtra("selectedStreamingService") ?: "Spotify"
        showStreamingService(selectedStreamingService)
    }

    private fun showStreamingService(service: String) {
        val fragment: Fragment = when (service) {
            "Spotify" -> SpotifyFragment()
            "YouTube Music" -> YoutubeMusicFragment()

            "Radio" -> RadioFragment()
            else -> SpotifyFragment() // Default to Spotify
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
