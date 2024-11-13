package app.parvin.weatherappp.presentation.map

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import app.parvin.weatherappp.mvi.MapAction
import app.parvin.weatherappp.mvi.MapUiState
import app.parvin.weatherappp.presentation.map.components.WeatherMarker
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberMarkerState


@Composable
fun MapScreen(
    state: MapUiState,
    onAction: (MapAction) -> Unit,
    navController: NavController
) {
    Surface() {
        Box {
            Map(
                modifier = Modifier.fillMaxSize(),
            ) {
                val markers = state.markers
                for (marker in markers) {
                    val markerState = rememberMarkerState(position = marker.location)
                    MarkerComposable(
                        state = markerState
                    ) {
                        WeatherMarker(
                            city = marker
                        )
                    }
                }
            }
        }
    }
}