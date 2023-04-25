package com.jonas.hillitsweather.domain.repository

import com.jonas.hillitsweather.domain.util.Resource
import com.jonas.hillitsweather.domain.weather.CompleteWeatherData

interface WeatherRepository {

    suspend fun getCurrentWeatherData(lat: String, lon: String): Resource<CompleteWeatherData>
}