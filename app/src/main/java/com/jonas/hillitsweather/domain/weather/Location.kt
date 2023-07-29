package com.jonas.hillitsweather.domain.weather

data class Location(
    val lon: Float,
    val lat: Float,
    val city: String,
    val formattedAdress: String
)
