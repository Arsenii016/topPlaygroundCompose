package com.example.topplaygroundcompose.domain.model

data class Track(
    val id: Long,
    val name: String,
    val artist: String,
    val album: String?,
    val artworkUrl: String?,
    val previewUrl: String?,
    val durationMillis: Long?,
    val isFavorite: Boolean
)
