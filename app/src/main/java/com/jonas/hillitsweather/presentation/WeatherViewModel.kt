package com.jonas.hillitsweather.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonas.hillitsweather.data.repository.UserPreferenceRepository
import com.jonas.hillitsweather.domain.repository.WeatherRepository
import com.jonas.hillitsweather.domain.util.Resource
import com.jonas.hillitsweather.domain.weather.Location
import kotlinx.coroutines.launch


class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val userPreferenceRepository: UserPreferenceRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            userPreferenceRepository.userPreferencesFlow.collect { preferences ->
                preferences.isRealMode?.let {
                    mode = if (it) WeatherMode.REAL else WeatherMode.HILLIT
                }
                preferences.currentLocation?.let {
                    loadWeatherFor(it)
                }
            }
        }
    }

    var state by mutableStateOf(WeatherState())
        private set

    private
    var mode: WeatherMode = WeatherMode.REAL


    var weatherModeIsHillit by mutableStateOf(false)
        private set

    fun toggleMode() {

        mode = if (mode == WeatherMode.HILLIT) WeatherMode.REAL else WeatherMode.HILLIT
        weatherModeIsHillit = weatherModeIsHillit.not()
        viewModelScope.launch {
            userPreferenceRepository.storeCurrentMode(mode == WeatherMode.REAL)
        }
    }

    fun updateLocation(newLocation: Location) {
        viewModelScope.launch {
            userPreferenceRepository.storeCurrentLocation(newLocation)
        }
    }

    private fun loadWeatherFor(location: Location) {
        viewModelScope.launch {
            val lon = location.lon.toString()
            val lat = location.lat.toString()

            state = state.copy(isLoading = true, error = null)
            val result = if (mode == WeatherMode.REAL)
                weatherRepository.getWeatherData(lat, lon) else
                weatherRepository.getFakeWeatherData(lat, lon)
            when (result) {
                is Resource.Success -> {
                    state = state.copy(
                        completeWeatherData = result.data,
                        isLoading = false,
                        error = null
                    )
                    state.completeWeatherData?.city = location.city
                }

                is Resource.Error -> {
                    state = state.copy(
                        completeWeatherData = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
}
