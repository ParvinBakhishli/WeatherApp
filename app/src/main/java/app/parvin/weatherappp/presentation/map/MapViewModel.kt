package app.parvin.weatherappp.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.parvin.weatherappp.domain.interactor.WeatherInteractor
import app.parvin.weatherappp.mvi.MapAction
import app.parvin.weatherappp.mvi.MapEffect
import app.parvin.weatherappp.mvi.MapUiStateReducer
import app.parvin.weatherappp.mvi.MapUiStateReducer.setMarkers
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
class MapViewModel @Inject constructor(
    private val interactor: WeatherInteractor
) :ViewModel( ){
    private val _uiState = MutableStateFlow(MapUiStateReducer.getInitialState())
    val uiState = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<MapEffect>()
    val effect = _effect.asSharedFlow()

    fun handleAction(action: MapAction) = when(action) {
        MapAction.OnGoBack -> handleOnGoBackAction()
        is MapAction.OnMarkerClicked -> handleMarkerClickedAction()
        is MapAction.OnSearched -> handleSearchedAction()
        MapAction.ViewCreated -> handleViewCreatedAction()
    }

    private fun handleViewCreatedAction() {
        viewModelScope.launch {
            try {
                val cityWeather = interactor.getWeatherForCities()
                _uiState.update { it.setMarkers(cityWeather) }
            } catch (e: IOException) {
                _effect.tryEmit(MapEffect.ShowErrorDialog)
            }
        }
    }

    private fun handleOnGoBackAction() {

    }

    private fun handleMarkerClickedAction() {

    }

    private fun handleSearchedAction() {

    }

}