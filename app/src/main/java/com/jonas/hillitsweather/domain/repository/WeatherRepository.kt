package com.jonas.hillitsweather.domain.repository

import com.jonas.hillitsweather.domain.util.Resource
import com.jonas.hillitsweather.domain.weather.CompleteWeatherData

interface WeatherRepository {

    suspend fun getWeatherData(lat: String, lon: String): Resource<CompleteWeatherData>
    suspend fun getFakeWeatherData(lat: String, lon: String): Resource<CompleteWeatherData>
}
