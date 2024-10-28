package app.parvin.weatherappp.domain.model

data class HourlyForecast(
    val hour: String,
    val weatherType: WeatherType,
    val temperature: Double,
    val isDay: Int,
    val isNow: Boolean
)
