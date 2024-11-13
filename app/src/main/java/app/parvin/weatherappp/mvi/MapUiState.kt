package app.parvin.weatherappp.mvi

import androidx.compose.runtime.Immutable
import app.parvin.weatherappp.domain.model.CityWeather


@Immutable
data class MapUiState(
    val markers: List<CityWeather>
)

sealed interface MapAction {
    data object ViewCreated : MapAction
    data object OnGoBack : MapAction
    data class OnSearched(val city: String) : MapAction
    data class OnMarkerClicked(val marker: CityWeather) : MapAction
}
