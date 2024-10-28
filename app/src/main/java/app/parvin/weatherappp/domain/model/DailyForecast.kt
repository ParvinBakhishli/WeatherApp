package app.parvin.weatherappp.domain.model

data class DailyForecast(
    val dayOfWeek: String,
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


