package com.jonas.hillitsweather.data.repository

import com.jonas.hillitsweather.data.mappers.toCompleteWeatherData
import com.jonas.hillitsweather.data.remote.RealWeatherApi
import com.jonas.hillitsweather.domain.repository.WeatherRepository
import com.jonas.hillitsweather.domain.util.Resource
import com.jonas.hillitsweather.domain.weather.CompleteWeatherData

class WeatherRepositoryImpl(private val api: RealWeatherApi) : WeatherRepository {
    override suspend fun getCurrentWeatherData(
        lat: String,
        lon: String
    ): Resource<CompleteWeatherData> {
        return try {
            Resource.Success(
                data = api.getWeatherDataOneCall(lat, lon, lang, units)
                    .toCompleteWeatherData()
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }

    }

    companion object {
        const val baseUrl = "https://api.openweathermap.org/"
        var lang = "de"
        var units = "metric"
    }
}