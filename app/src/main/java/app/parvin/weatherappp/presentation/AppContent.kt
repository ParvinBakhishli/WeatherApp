package app.parvin.weatherappp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.parvin.weatherappp.presentation.home.Home
import app.parvin.weatherappp.presentation.home.HomeScreen

@Composable
fun AppContent(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Home
    ) {
        composable<Home> {
            HomeScreen(location = "Baku")
        }
    }
}