package app.parvin.weatherappp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.parvin.weatherappp.domain.interactor.WeatherInteractor
import app.parvin.weatherappp.domain.model.DailyForecast
import app.parvin.weatherappp.domain.model.HourlyForecast
import app.parvin.weatherappp.domain.model.WeatherType
import app.parvin.weatherappp.domain.repository.WeatherRepository
import app.parvin.weatherappp.mvi.HomeScreenUiStateReducer
import app.parvin.weatherappp.mvi.HomeScreenUiStateReducer.setDailyForecast
import app.parvin.weatherappp.mvi.HomeScreenUiStateReducer.setHourlyForecast
import app.parvin.weatherappp.mvi.WeatherAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val interactor: WeatherInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiStateReducer.getInitialState())
    val uiState = _uiState.asStateFlow()

    fun handleAction(action: WeatherAction) = when(action) {
        is WeatherAction.ViewCreated -> handleViewCreatedAction()
        is WeatherAction.SearchIconClicked -> handleSearchIconClickedAction(action)
        is WeatherAction.OnDailyForecastItemClicked -> handleOnDailyForecastItemClickedAction(action)
    }

    private fun handleViewCreatedAction() {
        viewModelScope.launch {
            val hourly = interactor.getTodayHourlyForecast()
            val daily = interactor.getDailyData()
            _uiState.update { it.setHourlyForecast(hourly) }
            _uiState.update { it.setDailyForecast(daily) }
        }
    }

    private fun handleOnDailyForecastItemClickedAction(action: WeatherAction.OnDailyForecastItemClicked) {
        TODO("Not yet implemented")
    }

    private fun handleSearchIconClickedAction(action: WeatherAction.SearchIconClicked): Any {
        TODO("Not yet implemented")
    }

//    private val _todayData = MutableStateFlow<List<HourlyForecast?>>(listOf())
//    val todayData = _todayData.asStateFlow()
//
//    private val _dailyData = MutableStateFlow<List<DailyForecast>>(listOf())
//    val dailyData = _dailyData.asStateFlow()
//
//    init {
//        fetchTodayHourlyData()
//        fetchDailyData()
//    }

//    fun defineWeatherIcon() : Int {
//        todayData.value.forEach {
//            return if (it?.isDay == 1) {
//                it.weatherType.iconDay
//            } else {
//                it?.weatherType?.iconNight ?: -1
//            }
//        }
//        return -1
//    }

//
//    private fun fetchTodayHourlyData() {
//        viewModelScope.launch {
//            _todayData.value = repository.getTodayForecast()
//        }
//    }
//
//    private fun fetchDailyData() {
//        viewModelScope.launch {
//            _dailyData.value = repository.getDailyForecast()
//        }
//    }
}