package app.parvin.weatherappp.mvi

import app.parvin.weatherappp.domain.model.City
import app.parvin.weatherappp.domain.model.CityWeather
import app.parvin.weatherappp.domain.model.SelectableCity

object MapUiStateReducer {

    fun getInitialState() = MapUiState(
        markers = emptyList(),
        searchText = null,
        searchOptions = emptyList(),
        searchedCityMarker = null    // this marker should be on focus if it has a value
    )

    fun MapUiState.setMarkers(markers : List<CityWeather>) = copy(
        markers = markers
    )

    fun MapUiState.setSearchOptions(cities: List<City>) = copy(
        searchOptions = cities
    )

    fun MapUiState.resetSearchOption() = copy(
        searchOptions = emptyList()
    )

    fun MapUiState.setMarker(weather: CityWeather) = copy(
        searchedCityMarker = weather
    )
}