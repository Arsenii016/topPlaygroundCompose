package com.example.topplaygroundcompose.data.repository

import com.example.topplaygroundcompose.data.local.TrackDao
import com.example.topplaygroundcompose.data.local.toDomain
import com.example.topplaygroundcompose.data.local.toEntity
import com.example.topplaygroundcompose.data.remote.MusicApiService
import com.example.topplaygroundcompose.domain.model.Track
import com.example.topplaygroundcompose.domain.repository.MusicRepository
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val api: MusicApiService,
    private val dao: TrackDao
) : MusicRepository {

    override suspend fun searchTracks(query: String): List<Track> {
        // запрос к API
        val response = api.searchTracks(term = query)

        // все избранные треки из БД
        val favoritesIds = dao.getAll().map { it.trackId }.toSet()

        // защитимся от null в results
        val results = response.results ?: emptyList()

        return results.mapNotNull { dto ->
            // если нет id, пропускаем элемент, чтобы не упасть
            val id = dto.trackId ?: return@mapNotNull null

            Track(
                id = id,
                name = dto.trackName ?: "Unknown title",
                artist = dto.artistName ?: "Unknown artist",
                album = dto.collectionName,
                artworkUrl = dto.artworkUrl100,
                previewUrl = dto.previewUrl,
                durationMillis = dto.trackTimeMillis,
                isFavorite = favoritesIds.contains(id)
            )
        }
    }

    override suspend fun getFavorites(): List<Track> {
        return dao.getAll().map { entity ->
            entity.toDomain(isFavorite = true)
        }
    }

    override suspend fun toggleFavorite(track: Track) {
        val existing = dao.getById(track.id)
        if (existing != null) {
            dao.delete(existing)
        } else {
            dao.insert(track.toEntity())
        }
    }

    override suspend fun isFavorite(id: Long): Boolean {
        return dao.isFavorite(id)
    }
}
