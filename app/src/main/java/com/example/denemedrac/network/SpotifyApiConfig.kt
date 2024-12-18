package com.example.denemedrac.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SpotifyApiConfig {
    private fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService(): SpotifyApiService {
        return getRetrofit("YOUR_EXISTING_BASE_URL")
            .create(SpotifyApiService::class.java)
    }

    fun getSpotifyService(): SpotifyApiService {
        return getRetrofit("https://api.spotify.com/")
            .create(SpotifyApiService::class.java)
    }
}
