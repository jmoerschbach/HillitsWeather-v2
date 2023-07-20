package com.jonas.hillitsweather.domain.repository

import com.jonas.hillitsweather.domain.util.Resource
import com.jonas.hillitsweather.domain.weather.Location

interface LocationRepository {

    suspend fun getPossibleLocations(name: String): Resource<List<Location>>
}