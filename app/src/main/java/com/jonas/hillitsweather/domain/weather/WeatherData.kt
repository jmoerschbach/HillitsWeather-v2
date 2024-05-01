package com.jonas.hillitsweather.domain.weather

import java.time.ZonedDateTime

data class CompleteWeatherData(
    val lat: Double,
    val lon: Double,
    var city: String = "",
    val sunriseTime: ZonedDateTime,
    val sunsetTime: ZonedDateTime,
    val current: WeatherData,
    val hourForecast: List<WeatherData>,
    val dailyForecast: List<DailyWeatherData>
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

data class DailyWeatherData(
    val forecastedTime: ZonedDateTime,
    val temperatureMinCelsius: Float,
    val temperatureMaxCelsius: Float,
    val temperatureNightCelsius: Float,
    val temperatureDayCelsius: Float,
    val windspeed: Float,
    val description: String,
    val iconUrl: String,
    val humidity: Int,
    val pressure: Int,
    val rainProbability: Int,
    val temperatureMorningCelsius: Float,
    val temperatureEveningCelsius: Float
)
