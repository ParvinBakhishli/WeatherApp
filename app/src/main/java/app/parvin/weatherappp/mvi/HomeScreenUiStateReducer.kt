package app.parvin.weatherappp.mvi

import app.parvin.weatherappp.domain.model.DailyForecast
import app.parvin.weatherappp.domain.model.HourlyForecast


object HomeScreenUiStateReducer {
    fun getInitialState() = HomeScreenUiState(
        cityName = "Baku",
        hourlyForecast = emptyList(),
        dailyForecast = emptyList(),
        showDialog = false
    )

    fun HomeScreenUiState.setCityName(name: String) = copy(
        cityName = name
    )

    fun HomeScreenUiState.setHourlyForecast(hourlyData: List<HourlyForecast>) = copy(
        hourlyForecast = hourlyData
    )

    fun HomeScreenUiState.setDailyForecast(dailyData: List<DailyForecast>) = copy(
        dailyForecast = dailyData
    )

    fun HomeScreenUiState.showErrorDialog(show: Boolean) = copy(
        showDialog = show
    )
}