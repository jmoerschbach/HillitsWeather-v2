package com.jonas.hillitsweather.data.mappers

import com.jonas.hillitsweather.data.remote.CompletedLocation
import com.jonas.hillitsweather.data.remote.LocationDataDto
import com.jonas.hillitsweather.domain.weather.Location

fun LocationDataDto.toLocationData(location: CompletedLocation): Location {
    return Location(location.lon, location.lat, location.city!!, location.formatted!!)
}

fun LocationDataDto.toLocationList(): List<Location> {
   return results.map { toLocationData(it) }
}