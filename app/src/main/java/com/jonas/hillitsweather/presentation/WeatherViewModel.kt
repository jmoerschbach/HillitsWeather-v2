package com.jonas.hillitsweather.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonas.hillitsweather.domain.repository.WeatherRepository
import com.jonas.hillitsweather.domain.util.Resource
import kotlinx.coroutines.launch


class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    private var mode: WeatherMode = WeatherMode.REAL

    fun toggleMode() {
        mode = if (mode == WeatherMode.HILLIT) WeatherMode.REAL else WeatherMode.HILLIT
        loadWeather()
    }

    fun loadWeather() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            val result = if (mode == WeatherMode.REAL)
                repository.getWeatherData("52.157946884085916", "9.965014626514112") else
                repository.getFakeWeatherData("52.157946884085916", "9.965014626514112")
            when (result) {
                is Resource.Success -> {
                    state = state.copy(
                        completeWeatherData = result.data,
                        isLoading = false,
                        error = null
                    )
                    result.data?.current?.iconUrl?.let { Log.d("TAG", it) }
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