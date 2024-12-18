package com.example.denemedrac.music

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.denemedrac.music.radio.RadioFragment
import com.example.denemedrac.music.spotify.SpotifyFragment
import com.example.denemedrac.music.youtubeMusic.YoutubeMusicFragment

class MusicPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SpotifyFragment()
            1 -> YoutubeMusicFragment()
            2 -> RadioFragment()
            else -> SpotifyFragment()
        }
    }
}