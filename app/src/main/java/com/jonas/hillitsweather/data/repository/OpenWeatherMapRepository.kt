package com.jonas.hillitsweather.data.repository

import com.jonas.hillitsweather.data.mappers.toCompleteWeatherData
import com.jonas.hillitsweather.data.mappers.toFakeCompleteWeatherData
import com.jonas.hillitsweather.data.remote.OpenWeatherMapApi
import com.jonas.hillitsweather.domain.repository.WeatherRepository
import com.jonas.hillitsweather.domain.util.Resource
import com.jonas.hillitsweather.domain.weather.CompleteWeatherData

class OpenWeatherMapRepository(private val api: OpenWeatherMapApi) : WeatherRepository {
    override suspend fun getWeatherData(
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

    override suspend fun getFakeWeatherData(
        lat: String,
        lon: String
    ): Resource<CompleteWeatherData> {
        return try {
            Resource.Success(
                data = api.getWeatherDataOneCall(lat, lon, lang, units)
                    .toFakeCompleteWeatherData()
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    companion object {
        var lang = "de"
        var units = "metric"
    }
}
