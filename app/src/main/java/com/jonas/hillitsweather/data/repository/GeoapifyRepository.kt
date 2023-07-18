package com.jonas.hillitsweather.data.repository

import android.util.Log
import com.jonas.hillitsweather.data.mappers.toCompleteWeatherData
import com.jonas.hillitsweather.data.mappers.toLocationData
import com.jonas.hillitsweather.data.remote.GeoapifyApi
import com.jonas.hillitsweather.domain.repository.LocationRepository
import com.jonas.hillitsweather.domain.util.Resource
import com.jonas.hillitsweather.domain.weather.CompleteWeatherData
import com.jonas.hillitsweather.domain.weather.LocationData

class GeoapifyRepository(private val api: GeoapifyApi) : LocationRepository {

    override suspend fun getLocation(name: String): Resource<LocationData> {

        return try {
            Resource.Success(
                data = api.getLocation(
                    name,
                    lang,
                    "json"
                ).toLocationData()

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