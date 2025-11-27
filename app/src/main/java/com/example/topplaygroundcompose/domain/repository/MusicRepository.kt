package com.example.topplaygroundcompose.domain.repository

import com.example.topplaygroundcompose.domain.model.Track

interface MusicRepository {

    suspend fun searchTracks(query: String): List<Track>

    suspend fun getFavorites(): List<Track>

    suspend fun toggleFavorite(track: Track)

    suspend fun isFavorite(id: Long): Boolean
}
