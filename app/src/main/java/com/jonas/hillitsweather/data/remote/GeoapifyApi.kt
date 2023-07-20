package com.jonas.hillitsweather.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

private const val APP_ID = "358042b817114819a29c1e689d8fd2b6"

interface GeoapifyApi {

    @GET("autocomplete?apiKey=$APP_ID")
    suspend fun getMatchingLocations(
        @Query("text") addressPart: String,
        @Query("lang") language: String,
        @Query("format") format: String
    ): LocationDataDto
}