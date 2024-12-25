package app.parvin.weatherappp.presentation.map

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.parvin.weatherappp.mvi.MapAction
import app.parvin.weatherappp.mvi.MapEffect
import app.parvin.weatherappp.mvi.MapUiState
import app.parvin.weatherappp.presentation.map.components.SearchedCityOptions
import app.parvin.weatherappp.presentation.map.components.WeatherMarker
import app.parvin.weatherappp.util.NoInternetDialog
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged


@OptIn(FlowPreview::class)
@Composable
fun MapScreen(
    state: MapUiState,
    onAction: (MapAction) -> Unit,
    navController: NavController
) {

    var textFieldState by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var showSearchedMarker by remember { mutableStateOf(false) }
    val viewModel = hiltViewModel<MapViewModel>()

    val debouncedSearchFlow = remember {
        snapshotFlow { textFieldState }
            .debounce(500L)
            .distinctUntilChanged()
    }

    LaunchedEffect(Unit) {
        debouncedSearchFlow.collect { value ->
            if (value.isNotBlank()) {
                onAction(MapAction.ShowCityOptions(value))
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { mapEffect ->
            when(mapEffect) {
                is MapEffect.NavigateToMarker -> {
                    showSearchedMarker = true
                }
                MapEffect.ShowErrorDialog -> { showDialog = true }
            }
        }
    }

    if (showDialog) {
        NoInternetDialog {
            showDialog = false
        }
    }

    Surface() {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Map(
                camLocation = state.searchedCityMarker?.location,
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

                if (showSearchedMarker) {
                    val markerState = rememberMarkerState(position = state.searchedCityMarker?.location ?: LatLng(0.0, 0.0))
                    MarkerComposable(
                        state = markerState
                    ) {
                        state.searchedCityMarker?.let {
                            Log.e("it", "$it")
                            WeatherMarker(
                                city = it
                            )
                        }
                    }
                }
            }

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 40.dp, horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .border(
                            width = 0.5.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(10.dp)
                        )
                ){
                    TextField(
                        value = textFieldState,
                        onValueChange = { textFieldState = it },
                        placeholder = { Text("Search") },
                        leadingIcon = { Icon( imageVector = Icons.Default.Search, contentDescription = null) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .background(color = Color.White),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.DarkGray,
                            unfocusedTextColor = Color.DarkGray,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.LightGray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                if (state.searchOptions.isNotEmpty() && textFieldState.isNotBlank()) {
                    SearchedCityOptions(
                        cities = state.searchOptions,
                        onClicked = { city ->
                            showSearchedMarker = false
                            onAction(MapAction.OnCityClicked(city))
                        }
                    )
                }
            }
        }
    }
}
