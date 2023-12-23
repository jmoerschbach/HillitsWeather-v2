package com.jonas.hillitsweather.domain.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val lon: Float,
    val lat: Float,
    val city: String,
    val formattedAdress: String
): Parcelable
