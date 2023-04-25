package com.jonas.hillitsweather.data.mappers

import com.jonas.hillitsweather.data.remote.Current
import com.jonas.hillitsweather.data.remote.Hourly
import com.jonas.hillitsweather.data.remote.WeatherDataDto
import com.jonas.hillitsweather.domain.weather.CompleteWeatherData
import com.jonas.hillitsweather.domain.weather.WeatherData
import com.jonas.hillitsweather.utils.DateTimeHelper
import kotlin.math.roundToInt

private const val ICON_BASE_URL = "https://openweathermap.org/img/wn/"


private fun toCurrentWeatherData(current: Current): WeatherData {

    val temperature = current.temp
    val windspeed = current.windSpeed
    val humidity = current.humidity
    val pressure = current.pressure
    val time = DateTimeHelper.toZonedDateTime(current.dt)

    val description = current.weather[0].description!!
    val iconUrl = "${ICON_BASE_URL}${current.weather[0].icon}@4x.png"
    return WeatherData(
        forecastedTime = time,
        temperatureCelsius = temperature,
        windspeed = windspeed,
        description = description,
        iconUrl = iconUrl,
        humidity = humidity,
        pressure = pressure,
    )
}

private fun toHourlyWeatherForecast(hourly: List<Hourly>): List<WeatherData> {
    return hourly.take(24).map {
        val time = DateTimeHelper.toZonedDateTime(it.dt)
        val temperature = it.temp
        val windspeed = it.windSpeed
        val humidity = it.humidity
        val pressure = it.pressure
        val rainProbability = (it.rainProbability * 100).roundToInt()
        val description = it.weather[0].description!!
        val iconUrl = "${ICON_BASE_URL}${it.weather[0].icon}@4x.png"

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

fun WeatherDataDto.toCompleteWeatherData(): CompleteWeatherData {
    val currentWeather = current?.let { toCurrentWeatherData(it) }
    val hourForecast = toHourlyWeatherForecast(hourly)
    val sunrise = DateTimeHelper.toZonedDateTime(current!!.sunrise)
    val sunset = DateTimeHelper.toZonedDateTime(current!!.sunset)

    return CompleteWeatherData(
        sunriseTime = sunrise,
        sunsetTime = sunset,
        current = currentWeather!!,
        hourForecast = hourForecast
    )
}
