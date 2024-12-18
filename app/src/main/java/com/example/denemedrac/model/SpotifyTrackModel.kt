package com.example.denemedrac.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SpotifyTrackModel(
    @SerializedName("items")
    val items: List<Track> = emptyList()
) {
    @Keep
    data class Track(
        @SerializedName("track")
        val track: TrackDetails = TrackDetails()
    )

    @Keep
    data class TrackDetails(
        @SerializedName("id")
        val id: String = "",

        @SerializedName("name")
        val name: String = "",

        @SerializedName("artists")
        val artists: List<Artist> = emptyList(),

        @SerializedName("album")
        val album: Album = Album()
    )

    @Keep
    data class Artist(
        @SerializedName("name")
        val name: String = ""
    )

    @Keep
    data class Album(
        @SerializedName("images")
        val images: List<Image> = emptyList(),

        @SerializedName("name")
        val name: String = ""
    )

    @Keep
    data class Image(
        @SerializedName("url")
        val url: String = ""
    )
}

@Keep
data class SpotifyAccessTokenModel(
    @SerializedName("access_token")
    val accessToken: String = "",

    @SerializedName("token_type")
    val tokenType: String = "",

    @SerializedName("expires_in")
    val expiresIn: Int = 0
)