package com.example.topplaygroundcompose.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey val trackId: Long,
    val trackName: String,
    val artistName: String,
    val album: String?,
    val artworkUrl: String?,
    val previewUrl: String?,
    val durationMillis: Long?
)
