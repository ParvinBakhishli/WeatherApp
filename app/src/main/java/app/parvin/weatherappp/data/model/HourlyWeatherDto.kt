package app.parvin.weatherappp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyWeatherDto(
    @SerialName("time") val hourlyTime: List<String>,
    @SerialName("temperature_2m") val hourlyTemperature: List<Double>,
    @SerialName("weather_code") val hourlyWeatherCode: List<Int>,
    @SerialName("is_day") val isDay: List<Int>
)
