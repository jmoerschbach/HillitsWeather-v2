package com.jonas.hillitsweather.domain.weather

import java.time.ZonedDateTime

data class CompleteWeatherData(
    val sunriseTime: ZonedDateTime,
    val sunsetTime: ZonedDateTime,
    val current: WeatherData,
    val hourForecast: List<WeatherData>
)

data class WeatherData(
    val forecastedTime: ZonedDateTime,
    val temperatureCelsius: Float,
    val windspeed: Float,
    val description: String,
    val iconUrl: String,
    val humidity: Int,
    val pressure: Int,
    val rainProbability: Int? = null
)
