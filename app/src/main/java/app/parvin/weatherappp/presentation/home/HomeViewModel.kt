package app.parvin.weatherappp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.parvin.weatherappp.domain.model.DailyForecast
import app.parvin.weatherappp.domain.model.HourlyForecast
import app.parvin.weatherappp.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    private val _todayData = MutableStateFlow<List<HourlyForecast?>>(listOf())
    val todayData = _todayData.asStateFlow()

    private val _dailyData = MutableStateFlow<List<DailyForecast>>(listOf())
    val dailyData = _dailyData.asStateFlow()

    fun fetchTodayHourlyData() {
        viewModelScope.launch {
            _todayData.value = repository.getTodayForecast()
        }
    }

    fun fetchDailyData() {
        viewModelScope.launch {
            _dailyData.value = repository.getDailyForecast()
        }
    }
}