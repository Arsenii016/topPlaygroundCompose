package com.example.topplaygroundcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.topplaygroundcompose.presentation.favorites.FavoritesScreen
import com.example.topplaygroundcompose.presentation.favorites.FavoritesViewModel
import com.example.topplaygroundcompose.presentation.search.SearchScreen
import com.example.topplaygroundcompose.presentation.search.SearchViewModel

object Destinations {
    const val SEARCH = "search"
    const val FAVORITES = "favorites"
}

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Destinations.SEARCH
    ) {

        // Экран поиска
        composable(route = Destinations.SEARCH) {
            val searchViewModel: SearchViewModel = hiltViewModel()
            SearchScreen(
                viewModel = searchViewModel,
                onOpenFavorites = { navController.navigate(Destinations.FAVORITES) }
            )
        }

        // Экран избранного
        composable(route = Destinations.FAVORITES) {
            val favoritesViewModel: FavoritesViewModel = hiltViewModel()
            FavoritesScreen(
                viewModel = favoritesViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
