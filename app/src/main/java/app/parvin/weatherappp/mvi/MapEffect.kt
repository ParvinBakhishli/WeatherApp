package app.parvin.weatherappp.mvi

import app.parvin.weatherappp.domain.model.CityWeather


sealed class MapEffect {
    data object ShowErrorDialog : MapEffect()
    data class NavigateToMarker(val weather: CityWeather) : MapEffect()
}