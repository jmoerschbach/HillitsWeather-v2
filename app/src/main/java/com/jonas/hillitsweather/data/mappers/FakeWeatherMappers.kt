package com.jonas.hillitsweather.data.mappers

import com.jonas.hillitsweather.data.remote.Current
import com.jonas.hillitsweather.data.remote.Hourly
import com.jonas.hillitsweather.data.remote.WeatherDataDto
import com.jonas.hillitsweather.domain.weather.CompleteWeatherData
import com.jonas.hillitsweather.domain.weather.WeatherData
import com.jonas.hillitsweather.utils.DateTimeHelper

private const val ICON_BASE_URL = "https://openweathermap.org/img/wn/"
private const val FAKE_TEMPERATURE = 28F

private fun toFakeCurrentWeatherData(current: Current): WeatherData {

    val windspeed = current.windSpeed
    val humidity = 0
    val pressure = current.pressure
    val time = DateTimeHelper.toZonedDateTime(current.dt)

    val description = "Pure Sonne"
    val iconUrl = "${ICON_BASE_URL}01d@4x.png"
    return WeatherData(
        forecastedTime = time,
        temperatureCelsius = FAKE_TEMPERATURE,
        windspeed = windspeed,
        description = description,
        iconUrl = iconUrl,
        humidity = humidity,
        pressure = pressure,
    )
}
@Suppress("MagicNumber")
private fun toFakeHourlyWeatherForecast(hourly: List<Hourly>): List<WeatherData> {
    return hourly.take(24).map {
        val time = DateTimeHelper.toZonedDateTime(it.dt)
        val temperature = FAKE_TEMPERATURE
        val windspeed = it.windSpeed
        val humidity = it.humidity
        val pressure = it.pressure
        val rainProbability = 0
        val description = "Pure Sonne"
        val iconUrl = "${ICON_BASE_URL}01d@4x.png"

        WeatherData(
            forecastedTime = time,
            temperatureCelsius = temperature,
            windspeed = windspeed,
            description = description,
            iconUrl = iconUrl,
            humidity = humidity,
            pressure = pressure,
            rainProbability = rainProbability,
        )
    }
}

fun WeatherDataDto.toFakeCompleteWeatherData(): CompleteWeatherData {
    val currentWeather = current?.let { toFakeCurrentWeatherData(it) }
    val hourForecast = toFakeHourlyWeatherForecast(hourly)
    val sunrise = DateTimeHelper.toZonedDateTime(current!!.sunrise)
    val sunset = DateTimeHelper.toZonedDateTime(current!!.sunset)

    return CompleteWeatherData(
        sunriseTime = sunrise,
        sunsetTime = sunset,
        current = currentWeather!!,
        hourForecast = hourForecast
    )
}
