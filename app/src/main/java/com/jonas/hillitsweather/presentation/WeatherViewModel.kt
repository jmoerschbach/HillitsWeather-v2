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

enum class Mode {REAL, HILLIT}

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

//    var mode by mutableStateOf(Mode)

    fun loadWeather() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)

            when (val result = repository.getCurrentWeatherData("52.37895", "9.684040")) {
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