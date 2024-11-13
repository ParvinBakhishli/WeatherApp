package app.parvin.weatherappp.mvi

import app.parvin.weatherappp.domain.model.CityWeather

object MapUiStateReducer {

    fun getInitialState() = MapUiState(
        markers = emptyList()
    )

    fun MapUiState.setMarkers(markers : List<CityWeather>) = copy(
        markers = markers
    )
}