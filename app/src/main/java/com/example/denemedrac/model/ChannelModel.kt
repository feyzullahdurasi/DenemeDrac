package com.example.denemedrac.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ChannelModel(
    @SerializedName("items")
    val items: List<Items> = emptyList()
) {
    @Keep
    data class Items(
        @SerializedName("id")
        val id: String = "",

        @SerializedName("snippet")
        val snippet: SnippetYt = SnippetYt()
    )

    @Keep
    data class SnippetYt(
        @SerializedName("title")
        val title: String = "",

        @SerializedName("channelTitle")
        val channelTitle: String = "",

        @SerializedName("thumbnails")
        val thumbnails: Thumbnails = Thumbnails()
    )

    @Keep
    data class Thumbnails(
        @SerializedName("high")
        val high: Thumbnail = Thumbnail()
    )

    @Keep
    data class Thumbnail(
        @SerializedName("url")
        val url: String = ""
    )
}