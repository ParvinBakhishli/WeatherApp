package app.parvin.weatherappp.domain.model

import app.parvin.weatherappp.ui.WeatherType

data class HourlyForecast(
    val hour: String,
    val weatherType: WeatherType,
    val temperature: Double
)
