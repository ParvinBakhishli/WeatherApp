package app.parvin.weatherappp.data.repository_impl

import app.parvin.weatherappp.domain.model.DailyForecast
import app.parvin.weatherappp.domain.model.DailyWeatherVariable
import app.parvin.weatherappp.domain.model.HourlyForecast
import app.parvin.weatherappp.domain.model.HourlyWeatherVariable
import app.parvin.weatherappp.domain.repository.WeatherRepository
import app.parvin.weatherappp.network.ApiService
import app.parvin.weatherappp.domain.model.WeatherType
import app.parvin.weatherappp.util.zip
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

}
