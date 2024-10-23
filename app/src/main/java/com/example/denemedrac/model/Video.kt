package com.example.denemedrac.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Video(
    val id: String,
    val title: String,
    val thumbnailURL: String
) : Parcelable