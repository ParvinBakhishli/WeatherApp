package app.parvin.weatherappp.mvi

import androidx.compose.runtime.Immutable
import app.parvin.weatherappp.domain.model.DailyForecast
import app.parvin.weatherappp.domain.model.HourlyForecast


@Immutable
data class HomeScreenUiState (
    val cityName: String,
    val hourlyForecast: List< HourlyForecast>,
    val dailyForecast: List<DailyForecast>
)

sealed interface WeatherAction {
    data object ViewCreated : WeatherAction
    data object SearchIconClicked : WeatherAction
    data class OnDailyForecastItemClicked(val dayOfWeek: String) : WeatherAction
}

