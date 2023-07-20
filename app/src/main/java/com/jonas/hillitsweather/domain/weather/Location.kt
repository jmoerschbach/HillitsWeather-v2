package com.jonas.hillitsweather.domain.weather

data class LocationData(
    val lon: Float,
    val lat: Float,
    val city: String,
    val formattedAdress: String
)

data class PossibleLocations(val locations: List<LocationData> = listOf())
