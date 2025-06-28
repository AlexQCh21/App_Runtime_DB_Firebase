package com.example.app_s10.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    var id: String? = null,
    val title: String = "",
    val genre: String = "",
    val platform: String = "",
    val rating: Float = 0f,
    val description: String = "",
    val releaseYear: Int = 0,
    val completed: Boolean = false,
    var userId: String? = null,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable