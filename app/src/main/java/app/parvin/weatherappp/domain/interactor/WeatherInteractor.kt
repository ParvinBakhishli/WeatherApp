package app.parvin.weatherappp.domain.interactor

import app.parvin.weatherappp.domain.model.City
import app.parvin.weatherappp.domain.model.CityWeather
import app.parvin.weatherappp.domain.model.DailyForecast
import app.parvin.weatherappp.domain.model.HourlyForecast
import app.parvin.weatherappp.domain.repository.WeatherRepository
import javax.inject.Inject
import kotlin.math.roundToInt

//@ViewModelScoped
class WeatherInteractor @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend fun getDailyData(): List<DailyForecast> {
        return repository.getDailyForecast()
    }

    suspend fun getTodayHourlyForecast(): List<HourlyForecast> {
        return repository.getTodayForecast()
    }

    suspend fun getTomorrowWeatherCode(): Int {
        return repository.getDailyForecast()[0].weatherCode.iconDay
    }

    suspend fun getTomorrowTemperature(): Pair<Int, Int> {
        return Pair(
            repository.getDailyForecast()[0].averageTemperature.first.roundToInt(),
            repository.getDailyForecast()[0].averageTemperature.second.roundToInt()
        )
    }

    suspend fun getWeatherForCities(): List<CityWeather> {
        return repository.getWeatherForMarkers()
    }

    suspend fun getSearchOptions(city: String): List<City> {
        return repository.showSearchedCities(city)
    }

    suspend fun getWeatherForCity(city: City): CityWeather {
        return repository.getWeatherForCity(city)
    }
}