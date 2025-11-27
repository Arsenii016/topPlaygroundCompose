package com.example.topplaygroundcompose.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

// Ответ всего запроса
data class SearchResponseDto(
    val resultCount: Int?,
    val results: List<TrackDto>?
)

// Один трек из API
data class TrackDto(
    val trackId: Long?,
    val trackName: String?,
    val artistName: String?,
    val collectionName: String?,
    val artworkUrl100: String?,
    val previewUrl: String?,
    val trackTimeMillis: Long?
)

interface MusicApiService {

    @GET("search")
    suspend fun searchTracks(
        @Query("term") term: String,
        @Query("media") media: String = "music",
        @Query("limit") limit: Int = 25
    ): SearchResponseDto
}
