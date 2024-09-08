package app.parvin.weatherappp.domain.model

enum class DailyWeatherVariable(
    val value: String
) {
    MaxTemperature("temperature_2m_max"),
    MinTemperature("temperature_2m_min"),
    WeatherCode("weather_code"),
    Sunset("sunset"),
    Sunrise("sunrise");

}