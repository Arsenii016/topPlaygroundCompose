package com.example.topplaygroundcompose.presentation.favorites

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

data class FavoritesUiState(
    val isLoading: Boolean = false,
    val tracks: List<Track> = emptyList(),
    val errorMessage: String? = null
)

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: MusicRepository
) : ViewModel() {

    var uiState by mutableStateOf(FavoritesUiState())
        private set

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            try {
                val favorites = repository.getFavorites()
                uiState = uiState.copy(
                    isLoading = false,
                    tracks = favorites
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
            repository.toggleFavorite(track)
            loadFavorites() // после изменения сразу перечитываем БД
        }
    }
}
