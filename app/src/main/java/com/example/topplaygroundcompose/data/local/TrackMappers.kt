package com.example.topplaygroundcompose.data.local

import com.example.topplaygroundcompose.domain.model.Track

fun TrackEntity.toDomain(isFavorite: Boolean = true) = Track(
    id = trackId,
    name = trackName,
    artist = artistName,
    album = album,
    artworkUrl = artworkUrl,
    previewUrl = previewUrl,
    durationMillis = durationMillis,
    isFavorite = isFavorite
)

fun Track.toEntity() = TrackEntity(
    trackId = id,
    trackName = name,
    artistName = artist,
    album = album,
    artworkUrl = artworkUrl,
    previewUrl = previewUrl,
    durationMillis = durationMillis
)
