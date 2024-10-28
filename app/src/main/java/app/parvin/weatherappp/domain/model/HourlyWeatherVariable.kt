package app.parvin.weatherappp.domain.model

enum class HourlyWeatherVariable(
    val value: String,
) {
    Temperature2M("temperature_2m"),
    WeatherCode("weather_code"),
    IsDay("is_day");
}