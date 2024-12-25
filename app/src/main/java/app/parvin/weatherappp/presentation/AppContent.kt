package app.parvin.weatherappp.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.parvin.weatherappp.mvi.MapAction
import app.parvin.weatherappp.mvi.WeatherAction
import app.parvin.weatherappp.presentation.home.Home
import app.parvin.weatherappp.presentation.home.HomeScreen
import app.parvin.weatherappp.presentation.home.HomeViewModel
import app.parvin.weatherappp.presentation.map.MapRoute
import app.parvin.weatherappp.presentation.map.MapScreen
import app.parvin.weatherappp.presentation.map.MapViewModel

@Composable
fun AppContent(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Home
    ) {
        composable<Home> {
            val viewModel = hiltViewModel<HomeViewModel>(it)
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.handleAction(WeatherAction.ViewCreated)
            }

            HomeScreen(
                state = state,
                onAction = viewModel::handleAction,
                navController = navHostController
            )
        }

        composable<MapRoute> {
            val viewModel = hiltViewModel<MapViewModel>(it)
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.handleAction(MapAction.ViewCreated)
            }

            if (state.markers.isNotEmpty()) {
                MapScreen(
                    state = state,
                    onAction = viewModel::handleAction,
                    navController = navHostController
                )
            }
        }
    }
}