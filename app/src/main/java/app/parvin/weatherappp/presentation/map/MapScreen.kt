package app.parvin.weatherappp.presentation.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun MapScreen() {
    Surface() {
        Box {
            Map(
                modifier = Modifier.fillMaxSize(),
            ) {

            }
        }
    }
}