package com.example.topplaygroundcompose.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.topplaygroundcompose.domain.model.Track

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onOpenFavorites: () -> Unit
) {
    val state = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }

    // Показ ошибок через Snackbar
    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Music Search") },
                actions = {
                    Button(onClick = onOpenFavorites) {
                        Text(text = "Избранное")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            // Поле ввода запроса
            OutlinedTextField(
                value = state.query,
                onValueChange = { viewModel.onQueryChange(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Введите название трека или исполнителя") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Кнопка поиска
            Button(
                onClick = { viewModel.search() },
                enabled = !state.isLoading && state.query.isNotBlank()
            ) {
                Text(text = "Найти")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Индикатор загрузки
            if (state.isLoading) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            // Список треков
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(state.tracks) { track ->
                    TrackItem(
                        track = track,
                        onToggleFavorite = { viewModel.toggleFavorite(it) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun TrackItem(
    track: Track,
    onToggleFavorite: (Track) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* можно потом открыть детали трека */ }
            .padding(horizontal = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = track.name)
                    Text(text = track.artist)
                    track.album?.let {
                        Text(text = it)
                    }
                }

                // Простая "звездочка" текстом — без иконок и лишних зависимостей
                Text(
                    text = if (track.isFavorite) "★" else "☆",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable { onToggleFavorite(track) }
                )
            }

            // Можно добавить ещё duration или ссылку preview, если нужно
        }
    }
}
