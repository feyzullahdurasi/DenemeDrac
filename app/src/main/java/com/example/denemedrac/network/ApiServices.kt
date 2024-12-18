package com.example.denemedrac.network

import com.example.denemedrac.model.ChannelModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("youtube/v3/channels")
    fun getChannel(
        @Query("part") part: String,
        @Query("id") channelId: String,
        @Query("key") apiKey: String = "YOUTUBE_API_KEY" // API Anahtarınızı buraya koyun
    ): Call<ChannelModel>
}