package app.parvin.weatherappp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto(
    val latitude: Double,
    val longitude: Double,
    val hourly: HourlyWeatherDto? = null,
    val daily: DailyWeatherDto? = null
)
