package com.example.denemedrac.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.denemedrac.R

class MainViewModel : ViewModel() {
    private val _activeViews = MutableLiveData<Set<ActiveView>>(setOf(ActiveView.MAPS, ActiveView.MUSIC))
    val activeViews: LiveData<Set<ActiveView>> = _activeViews

    private val maxActiveViews = 2

    fun toggleView(view: ActiveView) {
        val currentViews = _activeViews.value ?: emptySet()
        val newViews = if (view in currentViews) {
            currentViews - view
        } else {
            if (currentViews.size >= maxActiveViews) {
                (currentViews - currentViews.first()) + view
            } else {
                currentViews + view
            }
        }
        _activeViews.value = newViews
    }

    fun isViewActive(view: ActiveView): Boolean {
        return view in (_activeViews.value ?: emptySet())
    }

    fun handleUrl(url: String) {
        when {
            url.startsWith("dracapp://") -> {
                val path = url.removePrefix("dracapp://")
                when (path) {
                    "maps" -> toggleView(ActiveView.MAPS)
                    "youtubeMusic" -> toggleView(ActiveView.MUSIC)
                }
            }
        }
    }
}

enum class StreamingService {
    SPOTIFY, YOUTUBE_MUSIC, APPLE_MUSIC, RADIO
}

enum class ActiveView(val iconResId: Int, val backgroundColor: Int) {
    MAPS(R.drawable.map_white, android.graphics.Color.BLUE),
    CONTACTS(R.drawable.phone_white, android.graphics.Color.GREEN),
    MUSIC(R.drawable.music_note_white, android.graphics.Color.MAGENTA),
    SPEEDOMETER(R.drawable.speed_white, android.graphics.Color.YELLOW),
    SPEED(R.drawable.car_white, android.graphics.Color.RED);
}