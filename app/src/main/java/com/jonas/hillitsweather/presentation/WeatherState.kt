package com.jonas.hillitsweather.presentation

import com.jonas.hillitsweather.domain.weather.CompleteWeatherData

data class WeatherState(
    val completeWeatherData: CompleteWeatherData? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
