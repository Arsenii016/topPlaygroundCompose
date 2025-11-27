package com.example.topplaygroundcompose.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topplaygroundcompose.domain.model.Track
import com.example.topplaygroundcompose.domain.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MusicRepository
) : ViewModel() {

    var uiState by mutableStateOf(SearchUiState())
        private set

    fun onQueryChange(newQuery: String) {
        uiState = uiState.copy(query = newQuery)
    }

    fun search() {
        val q = uiState.query.trim()
        if (q.isEmpty()) return

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            try {
                val result = repository.searchTracks(q)
                uiState = uiState.copy(
                    isLoading = false,
                    tracks = result
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Unknown error"
                )
            }
        }
    }

    fun toggleFavorite(track: Track) {
        viewModelScope.launch {
            // переключаем избранное в БД
            repository.toggleFavorite(track)

            // пересчитаем список треков,
            // чтобы обновилось поле isFavorite у нужного трека
            val currentQuery = uiState.query.trim()
            if (currentQuery.isNotEmpty()) {
                try {
                    val result = repository.searchTracks(currentQuery)
                    uiState = uiState.copy(tracks = result)
                } catch (_: Exception) {
                    // можно проигнорировать или показать ошибку
                }
            }
        }
    }
}
