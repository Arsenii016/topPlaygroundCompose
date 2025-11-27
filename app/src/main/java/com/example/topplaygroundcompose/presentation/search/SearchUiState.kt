package com.example.topplaygroundcompose.presentation.search

import com.example.topplaygroundcompose.domain.model.Track

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val tracks: List<Track> = emptyList(),
    val errorMessage: String? = null
)
