package app.parvin.weatherappp.mvi

import androidx.compose.runtime.Immutable
import app.parvin.weatherappp.domain.model.City
import app.parvin.weatherappp.domain.model.CityWeather


@Immutable
data class MapUiState(
    val markers: List<CityWeather>,
    val searchText: String?,
    val searchOptions: List<City>,
    val searchedCityMarker: CityWeather?
)

sealed interface MapAction {
    data object ViewCreated : MapAction
    data class OnMarkerClicked(val marker: CityWeather) : MapAction
    data class ShowCityOptions(val cityTyped: String) : MapAction
    data class OnCityClicked(val city: City) : MapAction
}
