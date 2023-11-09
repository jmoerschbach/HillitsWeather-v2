package com.jonas.hillitsweather.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonas.hillitsweather.domain.repository.WeatherRepository
import com.jonas.hillitsweather.domain.util.Resource
import com.jonas.hillitsweather.domain.weather.Location
import kotlinx.coroutines.launch


class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    private var mode: WeatherMode = WeatherMode.REAL

    var currentLocation: Location? = null

    var weatherModeIsHillit by mutableStateOf(false)
        private set

    fun toggleMode() {
        mode = if (mode == WeatherMode.HILLIT) WeatherMode.REAL else WeatherMode.HILLIT
        weatherModeIsHillit = weatherModeIsHillit.not()
        loadWeather()
    }

    fun loadWeather() {
        viewModelScope.launch {
            currentLocation?.let {
                val lon = currentLocation?.lon.toString()
                val lat = currentLocation?.lat.toString()

                state = state.copy(isLoading = true, error = null)
                val result = if (mode == WeatherMode.REAL)
                    repository.getWeatherData(lat, lon) else
                    repository.getFakeWeatherData(lat, lon)
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            completeWeatherData = result.data,
                            isLoading = false,
                            error = null
                        )
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
}
