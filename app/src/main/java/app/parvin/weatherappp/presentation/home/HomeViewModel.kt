package app.parvin.weatherappp.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.parvin.weatherappp.domain.interactor.WeatherInteractor
import app.parvin.weatherappp.mvi.HomeEffect
import app.parvin.weatherappp.mvi.HomeScreenUiStateReducer
import app.parvin.weatherappp.mvi.HomeScreenUiStateReducer.setDailyForecast
import app.parvin.weatherappp.mvi.HomeScreenUiStateReducer.setHourlyForecast
import app.parvin.weatherappp.mvi.WeatherAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val interactor: WeatherInteractor,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiStateReducer.getInitialState())
    val uiState = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect = _effect.asSharedFlow()


    fun handleAction(action: WeatherAction) = when(action) {
        is WeatherAction.ViewCreated -> handleViewCreatedAction()
        is WeatherAction.SearchIconClicked -> handleSearchIconClickedAction()
        is WeatherAction.OnDailyForecastItemClicked -> handleOnDailyForecastItemClickedAction(action)
    }

    private fun handleViewCreatedAction() {
        viewModelScope.launch {
            try {
                val hourly = interactor.getTodayHourlyForecast()
                val daily = interactor.getDailyData()
                _uiState.update { it.setHourlyForecast(hourly) }
                _uiState.update { it.setDailyForecast(daily) }
            } catch (e: IOException) {
                _effect.emit(HomeEffect.ShowDialog)
            }
        }
    }

    private fun handleOnDailyForecastItemClickedAction(action: WeatherAction.OnDailyForecastItemClicked) {
        TODO("Not yet implemented")
    }

    private fun handleSearchIconClickedAction() {

    }
}