package com.example.denemedrac.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.net.URL

class YouTubeAPI {
    private val apiKey = "AIzaSyAr_Mt1dNZkncYUG9D1teKL9OLhG5cHMws"

    suspend fun fetchMusicVideos(query: String): Result<List<Video>> = withContext(Dispatchers.IO) {
        try {
            val urlString = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=$query&type=video&videoCategoryId=10&key=$apiKey"
            val url = URL(urlString)

            val connection = url.openConnection()
            val data = connection.getInputStream().bufferedReader().use { it.readText() }

            val response = kotlinx.serialization.json.Json.decodeFromString<YouTubeResponse>(data)
            val videos = response.items.map { item ->
                Video(
                    id = item.id.videoId,
                    title = item.snippet.title,
                    thumbnailURL = item.snippet.thumbnails.default.url
                )
            }
            Result.success(videos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

@Serializable
data class Video(
    val id: String,
    val title: String,
    @SerialName("thumbnailURL")
    val thumbnailURL: String
)

@Serializable
data class YouTubeResponse(
    val items: List<VideoItem>
)

@Serializable
data class VideoItem(
    val id: VideoId,
    val snippet: VideoSnippet
)

@Serializable
data class VideoId(
    @SerialName("videoId")
    val videoId: String
)

@Serializable
data class VideoSnippet(
    val title: String,
    val thumbnails: VideoThumbnails
)

@Serializable
data class VideoThumbnails(
    @SerialName("default")
    val default: ThumbnailDetails
)

@Serializable
data class ThumbnailDetails(
    val url: String
)