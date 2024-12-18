package com.example.denemedrac.music.spotify

import android.util.Base64
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.denemedrac.model.SpotifyAccessTokenModel
import com.example.denemedrac.model.SpotifyTrackModel
import com.example.denemedrac.network.ApiConfig
import com.example.denemedrac.network.SpotifyApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpotifyViewModel : ViewModel() {
    private val _recentTracks = MutableLiveData<List<SpotifyTrackModel.Track>>()
    val recentTracks: LiveData<List<SpotifyTrackModel.Track>> = _recentTracks

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var accessToken: String? = null

    init {
        fetchAccessToken()
    }

    private fun fetchAccessToken() {
        _isLoading.value = true
        val clientId = "YOUR_SPOTIFY_CLIENT_ID"
        val clientSecret = "YOUR_SPOTIFY_CLIENT_SECRET"

        // Base64 encode client credentials
        val credentials = "$clientId:$clientSecret"
        val encodedCredentials = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

        val apiService = SpotifyApiConfig.getSpotifyService()
        apiService.getAccessToken("client_credentials", clientId, clientSecret)
            .enqueue(object : Callback<SpotifyAccessTokenModel> {
                override fun onResponse(
                    call: Call<SpotifyAccessTokenModel>,
                    response: Response<SpotifyAccessTokenModel>
                ) {
                    if (response.isSuccessful) {
                        accessToken = response.body()?.accessToken
                        fetchRecentTracks()
                    } else {
                        _error.value = "Failed to get access token"
                        _isLoading.value = false
                    }
                }

                override fun onFailure(call: Call<SpotifyAccessTokenModel>, t: Throwable) {
                    _error.value = "Network error: ${t.localizedMessage}"
                    _isLoading.value = false
                }
            })
    }

    private fun fetchRecentTracks() {
        accessToken?.let { token ->
            val apiService = SpotifyApiConfig.getSpotifyService()
            apiService.getRecentTracks("Bearer $token")
                .enqueue(object : Callback<SpotifyTrackModel> {
                    override fun onResponse(
                        call: Call<SpotifyTrackModel>,
                        response: Response<SpotifyTrackModel>
                    ) {
                        _isLoading.value = false
                        if (response.isSuccessful) {
                            _recentTracks.value = response.body()?.items ?: emptyList()
                        } else {
                            _error.value = "Failed to fetch tracks"
                        }
                    }

                    override fun onFailure(call: Call<SpotifyTrackModel>, t: Throwable) {
                        _isLoading.value = false
                        _error.value = "Network error: ${t.localizedMessage}"
                    }
                })
        }
    }
}