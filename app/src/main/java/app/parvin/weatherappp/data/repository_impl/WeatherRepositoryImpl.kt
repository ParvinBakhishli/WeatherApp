package app.parvin.weatherappp.data.repository_impl

import android.util.Log
import app.parvin.weatherappp.domain.model.City
import app.parvin.weatherappp.domain.model.CityWeather
import app.parvin.weatherappp.domain.model.DailyForecast
import app.parvin.weatherappp.domain.model.DailyWeatherVariable
import app.parvin.weatherappp.domain.model.HourlyForecast
import app.parvin.weatherappp.domain.model.HourlyWeatherVariable
import app.parvin.weatherappp.domain.repository.WeatherRepository
import app.parvin.weatherappp.network.ApiService
import app.parvin.weatherappp.domain.model.WeatherType
import app.parvin.weatherappp.util.zip
import com.google.android.gms.maps.model.LatLng
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import javax.inject.Inject
import kotlin.math.roundToInt


class WeatherRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    WeatherRepository {

    companion object {
        val hourlyResponseTimeFormat = LocalDateTime.Formats.ISO
        val hourFormat = LocalDateTime.Format {
            amPmHour()
            char(' ')
            amPmMarker("AM", "PM")
        }
        val hour12toHour24Format = LocalDateTime.Format {
            hour(padding = Padding.NONE)
        }

        private val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val tomorrow = today.plus(1, DateTimeUnit.DAY)
        private val currentHour = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).hour

        val dailyResponseTimeFormat = LocalDate.Formats.ISO
        val dayFormat = LocalDate.Format {
            dayOfMonth(padding = Padding.SPACE)
            char(' ')
            monthName(
                MonthNames(
                    "Jan",
                    "Feb",
                    "Mar",
                    "Apr",
                    "May",
                    "June",
                    "July",
                    "Aug",
                    "Sep",
                    "Oct",
                    "Nov",
                    "Dec"
                )
            )
        }
        val dayOfWeekFormat = LocalDate.Format {
            dayOfWeek(DayOfWeekNames(
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday")
            )
        }
    }

    override suspend fun getTodayForecast(): List<HourlyForecast> {
        val hourlyWeatherVariables = listOf(
            HourlyWeatherVariable.Temperature2M.value,
            HourlyWeatherVariable.WeatherCode.value,
            HourlyWeatherVariable.IsDay.value
        )
        val response = apiService.getWeatherData(40.409264, 49.867092, "Europe/Moscow",1, hourlyWeatherVariables, listOf())

        val hours = response.hourly?.hourlyTime?.map {
            hourlyResponseTimeFormat.parse(it).format(hourFormat)
        }.orEmpty()
        val hoursIn24 = response.hourly?.hourlyTime?.map {
            hourlyResponseTimeFormat.parse(it).format(hour12toHour24Format)
        }.orEmpty()

        val weatherTypes = response.hourly?.hourlyWeatherCode?.map {
            WeatherType.from(it) ?: WeatherType.ClearSky
        }.orEmpty()

        val temperatures = response.hourly?.hourlyTemperature.orEmpty()
        val isDay = response.hourly?.isDay.orEmpty()


        return zip(hours, temperatures, weatherTypes, isDay, hoursIn24) { hour, temperature, weatherType, isday, hour24 ->
            HourlyForecast(
                hour = hour,
                weatherType = weatherType,
                temperature = temperature,
                isDay = isday,
                isNow = hour24.toInt() == currentHour
            )
        }
    }

    override suspend fun getDailyForecast(): List<DailyForecast> {
        val dailyWeatherVariables = listOf(
            DailyWeatherVariable.MaxTemperature.value,
            DailyWeatherVariable.MinTemperature.value,
            DailyWeatherVariable.WeatherCode.value
        )
        val response = apiService.getWeatherData(40.41, 49.86, "Europe/Moscow", 8, listOf(), dailyWeatherVariables)

        val dates = response.daily?.dailyTime?.map {
            dailyResponseTimeFormat.parse(it).format(dayFormat)
        }.orEmpty()

        val dayOfWeek = response.daily?.dailyTime?.map {
            dailyResponseTimeFormat.parse(it).format(dayOfWeekFormat)
        }.orEmpty()

        val dailyWeatherType = response.daily?.dailyWeatherCode?.map {
            WeatherType.from(it) ?: WeatherType.ClearSky
        }.orEmpty()

        val minTempList = response.daily?.dailyMinTemperature.orEmpty()
        val maxTempList = response.daily?.dailyMaxTemperature.orEmpty()
        val temps = zip(minTempList, maxTempList) { min, max ->
            Pair(min, max)
        }

        return zip(dayOfWeek, dates, dailyWeatherType, temps) { weekday, date, weatherType, temp ->
            DailyForecast(
                dayOfWeek = weekday,
                date = date,
                weatherCode = weatherType,
                averageTemperature = temp
            )
        }.drop(1).mapIndexed { i, forecast ->
            if (i == 0) {
                forecast.copy(
                    dayOfWeek = "Tomorrow"
                )
            } else {
                forecast
            }
        }
    }

    override suspend fun getCities(): List<City> {
        return listOf(
            City("Baku", LatLng(40.4093, 49.8671)),
            City("Ganja", LatLng(40.6828, 46.3606)),
            City("Sumqayit", LatLng(40.5897, 49.6686)),
            City("Mingachevir", LatLng(40.7644, 47.0595)),
            City("Shirvan", LatLng(39.9271, 48.9203)),
            City("Nakhchivan", LatLng(39.2089, 45.4122)),
            City("Shaki", LatLng(41.1919, 47.1706)),
            City("Lankaran", LatLng(38.7529, 48.8517)),
            City("Shamakhi", LatLng(40.6306, 48.6410)),
            City("Barda", LatLng(40.3744, 47.1266)),
            City("Quba", LatLng(41.3641, 48.5122))
        )
    }

    override suspend fun getWeatherForCities(): List<CityWeather> {
        Log.e("cities", "Here is here")

        val cityWeather = mutableListOf<CityWeather>()
        val dailyWeatherVariables = listOf(
            DailyWeatherVariable.MaxTemperature.value,
            DailyWeatherVariable.WeatherCode.value
        )

        val cities = getCities()

        for (city in cities) {
            val response = apiService.getWeatherData(city.location.latitude, city.location.longitude, "Europe/Moscow", 1, listOf(), dailyWeatherVariables)
            val temperature = response.daily?.dailyMaxTemperature.orEmpty()
            val weatherType = response.daily?.dailyWeatherCode?.map {
                WeatherType.from(it) ?: WeatherType.ClearSky
            }
            val icon = weatherType?.get(0)?.iconDay ?: 1

            val weather = CityWeather(
                location = city.location,
                name = city.name,
                temperature = temperature[0].roundToInt(),
                weatherIcon = icon
            )
            cityWeather.add(weather)
        }

        return cityWeather.toList()
    }
}
