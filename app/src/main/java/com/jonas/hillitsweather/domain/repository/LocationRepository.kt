package com.jonas.hillitsweather.domain.repository

import com.jonas.hillitsweather.domain.util.Resource
import com.jonas.hillitsweather.domain.weather.LocationData

interface LocationRepository {

    suspend fun getLocation(name: String) : Resource<LocationData>
}