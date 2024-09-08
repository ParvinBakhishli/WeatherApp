package app.parvin.weatherappp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyWeatherDto(
    @SerialName("time") val dailyTime: List<String>? = null,
    @SerialName("weather_code") val dailyWeatherCode: List<Int>? = null,
    @SerialName("temperature_2m_max") val dailyMaxTemperature: List<Double>? = null,
    @SerialName("temperature_2m_min") val dailyMinTemperature: List<Double>? = null
)
