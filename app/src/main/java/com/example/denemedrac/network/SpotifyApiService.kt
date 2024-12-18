package com.example.denemedrac.network

import com.example.denemedrac.model.SpotifyAccessTokenModel
import com.example.denemedrac.model.SpotifyTrackModel
import retrofit2.Call
import retrofit2.http.*

interface SpotifyApiService {
    @FormUrlEncoded
    @POST("https://accounts.spotify.com/api/token")
    fun getAccessToken(
        @Field("grant_type") grantType: String,
        @Field("YOUR_SPOTIFY_CLIENT_ID") clientId: String,
        @Field("YOUR_SPOTIFY_CLIENT_SECRET") clientSecret: String
    ): Call<SpotifyAccessTokenModel>

    @GET("v1/me/player/recently-played")
    fun getRecentTracks(
        @Header("Authorization") authorization: String,
        @Query("limit") limit: Int = 10
    ): Call<SpotifyTrackModel>
}