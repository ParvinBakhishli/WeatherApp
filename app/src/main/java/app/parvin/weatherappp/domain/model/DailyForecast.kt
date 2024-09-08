package app.parvin.weatherappp.domain.model

import app.parvin.weatherappp.ui.WeatherType

data class DailyForecast(
    val date: String,
    val weatherCode: WeatherType,
    val averageTemperature: Pair<Double, Double>
)


//data class DailyWeather(
//    val time: String,
//    val weatherType: WeatherType,
//    val maxTemperature: Double,
//    val minTemperature: Double,
//)


