package com.example.denemedrac.music.youtubeMusic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.denemedrac.network.Video
import com.example.denemedrac.network.YouTubeAPI
import kotlinx.coroutines.launch
import kotlin.random.Random

class YtMViewModel : ViewModel() {
    private val _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>> = _videos

    private val _currentVideo = MutableLiveData<Video?>()
    val currentVideo: LiveData<Video?> = _currentVideo

    private val _duration = MutableLiveData<Int>()
    val duration: LiveData<Int> = _duration

    private val _currentTime = MutableLiveData<Int>()
    val currentTime: LiveData<Int> = _currentTime

    private val _isPlaying = MutableLiveData<Boolean>()
    val isPlaying: LiveData<Boolean> = _isPlaying

    private val youtubeAPI = YouTubeAPI()

    fun fetchMusicVideos(query: String = "Top Music") {
        viewModelScope.launch {
            try {
                val result = youtubeAPI.fetchMusicVideos(query)
                result.onSuccess { videoList ->
                    _videos.postValue(videoList)
                    playRandomVideo()
                }.onFailure { error ->
                    // Hata durumunu handle et
                    println("Error fetching music videos: $error")
                }
            } catch (e: Exception) {
                println("Error: $e")
            }
        }
    }

    fun playRandomVideo() {
        _videos.value?.let { videoList ->
            if (videoList.isNotEmpty()) {
                val randomVideo = videoList[Random.nextInt(videoList.size)]
                _currentVideo.postValue(randomVideo)
                _isPlaying.postValue(true)
            }
        }
    }

    fun pauseVideo() {
        _isPlaying.postValue(false)
    }

    fun playVideo() {
        _isPlaying.postValue(true)
    }

    fun updateDuration(durationSeconds: Int) {
        _duration.postValue(durationSeconds)
    }

    fun updateCurrentTime(timeSeconds: Int) {
        _currentTime.postValue(timeSeconds)
    }
}