package app.parvin.weatherappp.domain.repository

import app.parvin.weatherappp.data.model.ResultDto
import app.parvin.weatherappp.domain.model.City
import app.parvin.weatherappp.domain.model.CityWeather
import app.parvin.weatherappp.domain.model.DailyForecast
import app.parvin.weatherappp.domain.model.HourlyForecast


interface WeatherRepository {
    suspend fun getTodayForecast(): List<HourlyForecast>
    suspend fun getDailyForecast(): List<DailyForecast>
    suspend fun getCitiesForMarkers(): List<City>
    suspend fun getWeatherForMarkers(): List<CityWeather>
    suspend fun showSearchedCities(city: String): List<City>
    suspend fun getWeatherForCity(city: City): CityWeather
}
