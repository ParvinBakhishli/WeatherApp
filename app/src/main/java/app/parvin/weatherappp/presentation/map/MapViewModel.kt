package app.parvin.weatherappp.presentation.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.parvin.weatherappp.domain.interactor.WeatherInteractor
import app.parvin.weatherappp.mvi.MapAction
import app.parvin.weatherappp.mvi.MapEffect
import app.parvin.weatherappp.mvi.MapUiStateReducer
import app.parvin.weatherappp.mvi.MapUiStateReducer.setMarker
import app.parvin.weatherappp.mvi.MapUiStateReducer.setMarkers
import app.parvin.weatherappp.mvi.MapUiStateReducer.setSearchOptions
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
        is MapAction.OnMarkerClicked -> handleMarkerClickedAction(action)
        MapAction.ViewCreated -> handleViewCreatedAction()
        is MapAction.ShowCityOptions -> handleShowCityOptions(action)
        is MapAction.OnCityClicked -> handleOnCityClickedAction(action)
    }

    private fun handleOnCityClickedAction(action: MapAction.OnCityClicked) {
        viewModelScope.launch {
            try {
                val weather = interactor.getWeatherForCity(action.city)
                _uiState.update{ it.setMarker(weather)}
                if (uiState.value.searchedCityMarker != null) {
                    _effect.emit(MapEffect.NavigateToMarker(uiState.value.searchedCityMarker!!))
                    Log.e("djs", "marker effect emitted- ${uiState.value.searchedCityMarker}")
                }
            } catch (exc: IOException) {
                _effect.tryEmit(MapEffect.ShowErrorDialog)
            }
        }
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

    private fun handleShowCityOptions(action: MapAction.ShowCityOptions) {
        viewModelScope.launch {
            try {
                val cites = interactor.getSearchOptions(action.cityTyped)
                _uiState.update { it.setSearchOptions(cities = cites) }
            } catch (_: IOException){

            }
        }
    }

    private fun handleMarkerClickedAction(action: MapAction.OnMarkerClicked) {

    }

}