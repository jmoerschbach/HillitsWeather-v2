package com.jonas.hillitsweather.data.remote

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
class LocationDataDto {
    @field:Json(name="results")
    var results = listOf<CompletedLocation>()

}
@Keep
class CompletedLocation {
    @field:Json(name="city")
    var city: String? = null

    @field:Json(name="country")
    var country: String? = null

    @field:Json(name="state")
    var state: String? = null

    @field:Json(name="formatted")
    var formatted: String? = null

    @field:Json(name="lon")
    var lon: Float = 0.toFloat()

    @field:Json(name="lat")
    var lat: Float = 0.toFloat()
}
