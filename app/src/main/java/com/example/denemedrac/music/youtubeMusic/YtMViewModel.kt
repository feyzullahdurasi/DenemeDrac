package com.example.denemedrac.music.youtubeMusic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.denemedrac.model.ChannelModel
import com.example.denemedrac.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YtMViewModel : ViewModel() {

    private val _channel = MutableLiveData<ChannelModel?>()
    val channel: LiveData<ChannelModel?> = _channel

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    init {
        fetchChannelData()
    }

    fun fetchChannelData(channelId: String = "YOUTUBE_API_KEY") {
        _isLoading.value = true
        val client = ApiConfig.getService().getChannel("snippet", channelId)
        client.enqueue(object : Callback<ChannelModel> {
            override fun onResponse(call: Call<ChannelModel>, response: Response<ChannelModel>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null && data.items.isNotEmpty()) {
                        _channel.value = data
                    } else {
                        _message.value = "No channel data found"
                    }
                } else {
                    _message.value = "Error: ${response.code()} - ${response.message()}"
                }
            }

            override fun onFailure(call: Call<ChannelModel>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Channel fetch failed", t)
                _message.value = "Network error: ${t.localizedMessage}"
            }
        })
    }

    companion object {
        private val TAG = YtMViewModel::class.java.simpleName
    }
}