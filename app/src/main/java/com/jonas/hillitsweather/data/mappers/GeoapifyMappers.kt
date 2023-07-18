package com.jonas.hillitsweather.data.mappers

import com.jonas.hillitsweather.data.remote.LocationDataDto
import com.jonas.hillitsweather.domain.weather.LocationData

fun LocationDataDto.toLocationData(): LocationData {
    val x = results[0]
    return LocationData(x.lon, x.lat, x.city!!)
}