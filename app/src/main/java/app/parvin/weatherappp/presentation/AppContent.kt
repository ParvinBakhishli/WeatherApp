package app.parvin.weatherappp.presentation

import android.net.wifi.hotspot2.pps.HomeSp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.parvin.weatherappp.mvi.WeatherAction
import app.parvin.weatherappp.presentation.home.Home
import app.parvin.weatherappp.presentation.home.HomeScreen
import app.parvin.weatherappp.presentation.home.HomeViewModel

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
            viewModel.handleAction(WeatherAction.ViewCreated)

            HomeScreen(
                state = state,
                onAction = viewModel::handleAction
            )
        }
    }
}