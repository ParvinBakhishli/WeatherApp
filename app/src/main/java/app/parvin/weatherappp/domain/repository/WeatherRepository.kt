package app.parvin.weatherappp.domain.repository

import app.parvin.weatherappp.domain.model.City
import app.parvin.weatherappp.domain.model.CityWeather
import app.parvin.weatherappp.domain.model.DailyForecast
import app.parvin.weatherappp.domain.model.HourlyForecast


interface WeatherRepository {
    suspend fun getTodayForecast(): List<HourlyForecast>
    suspend fun getDailyForecast(): List<DailyForecast>
    suspend fun getCities(): List<City>
    suspend fun getWeatherForCities(): List<CityWeather>
}
