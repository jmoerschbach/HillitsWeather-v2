package com.jonas.hillitsweather.data.mappers

import com.jonas.hillitsweather.data.remote.CompletedLocation
import com.jonas.hillitsweather.data.remote.LocationDataDto
import com.jonas.hillitsweather.domain.weather.Location

fun LocationDataDto.toLocationData(x: CompletedLocation): Location {
    return Location(x.lon, x.lat, x.city!!, x.formatted!!)
}

fun LocationDataDto.toLocationList(): List<Location> {
   return results.map { toLocationData(it) }
}