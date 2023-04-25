package com.jonas.hillitsweather.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val appId = "a4c3f382fb9edbcbf500bfc77aa7a307"
private const val exludes="alerts,minutely"
interface RealWeatherApi {

    @GET("data/2.5/onecall?appId=${appId}&exclude=${exludes}")
    suspend fun getWeatherDataOneCall(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("lang") language: String,
        @Query("units") units: String
    ): WeatherDataDto

//    @GET("data/2.5/forecast?appId=${appId}")
//    fun getWeatherDataForecast(
//        @Query("lat") lat: String,
//        @Query("lon") lon: String,
//        @Query("lang") language: String,
//        @Query("units") units: String
//    ): Call<HourlyForecast>


}