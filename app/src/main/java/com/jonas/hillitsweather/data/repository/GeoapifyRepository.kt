package com.jonas.hillitsweather.data.repository

import com.jonas.hillitsweather.data.mappers.toLocationList
import com.jonas.hillitsweather.data.remote.GeoapifyApi
import com.jonas.hillitsweather.domain.repository.LocationRepository
import com.jonas.hillitsweather.domain.util.Resource
import com.jonas.hillitsweather.domain.weather.Location

class GeoapifyRepository(private val api: GeoapifyApi) : LocationRepository {

    override suspend fun getPossibleLocations(cityNamePart: String): Resource<List<Location>> {

        return try {
            Resource.Success(
                data = api.getMatchingLocations(
                    cityNamePart,
                    lang,
                    format
                ).toLocationList()

            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }

    }


    companion object {
        var lang = "de"
        var format = "json"
    }
}
