package app.parvin.weatherappp.data.repository_impl

import app.parvin.weatherappp.domain.model.DailyForecast
import app.parvin.weatherappp.domain.model.DailyWeatherVariable
import app.parvin.weatherappp.domain.model.HourlyForecast
import app.parvin.weatherappp.domain.model.HourlyWeatherVariable
import app.parvin.weatherappp.domain.repository.WeatherRepository
import app.parvin.weatherappp.network.ApiService
import app.parvin.weatherappp.ui.WeatherType
import app.parvin.weatherappp.util.zip
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import javax.inject.Inject


class WeatherRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    WeatherRepository {

    companion object {
        val hourlyResponseTimeFormat = LocalDateTime.Formats.ISO
        val hourFormat = LocalDateTime.Format {
            amPmHour()
            char(' ')
            amPmMarker("AM", "PM")
        }

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
    }

    override suspend fun getTodayForecast(): List<HourlyForecast> {
        val hourlyWeatherVariables = listOf(
            HourlyWeatherVariable.Temperature2M.value,
            HourlyWeatherVariable.WeatherCode.value
        )
        val response = apiService.getWeatherData(40.41, 49.86, 1, hourlyWeatherVariables, listOf())

        val hours = response.hourly?.hourlyTime?.map {
            hourlyResponseTimeFormat.parse(it).format(hourFormat)
        }.orEmpty()
        val weatherTypes = response.hourly?.hourlyWeatherCode?.map {
            WeatherType.from(it) ?: WeatherType.ClearSky
        }.orEmpty()
        val temperatures = response.hourly?.hourlyTemperature.orEmpty()

        return zip(hours, temperatures, weatherTypes) { hour, temperature, weatherType ->
            HourlyForecast(
                hour = hour,
                weatherType = weatherType,
                temperature = temperature
            )
        }
    }

    override suspend fun getDailyForecast(): List<DailyForecast> {
        val dailyWeatherVariables = listOf(
            DailyWeatherVariable.MaxTemperature.value,
            DailyWeatherVariable.MinTemperature.value,
            DailyWeatherVariable.WeatherCode.value
        )
        val response = apiService.getWeatherData(40.41, 49.86, 7, listOf(), dailyWeatherVariables)

        val dates = response.daily?.dailyTime?.map {
            dailyResponseTimeFormat.parse(it).format(dayFormat)
        }.orEmpty()
        val dailyWeatherType = response.daily?.dailyWeatherCode?.map {
            WeatherType.from(it) ?: WeatherType.ClearSky
        }.orEmpty()

        val minTempList = response.daily?.dailyMinTemperature.orEmpty()
        val maxTempList = response.daily?.dailyMaxTemperature.orEmpty()
        val temps = zip(minTempList, maxTempList) { min, max ->
            Pair(min, max)
        }

        return zip(dates, dailyWeatherType, temps) { date, weatherType, temp ->
            DailyForecast(
                date = date,
                weatherCode = weatherType,
                averageTemperature = temp
            )
        }
    }

}
