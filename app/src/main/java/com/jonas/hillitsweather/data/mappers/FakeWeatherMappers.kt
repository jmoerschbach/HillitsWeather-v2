package com.jonas.hillitsweather.data.mappers

import com.jonas.hillitsweather.data.remote.Current
import com.jonas.hillitsweather.data.remote.Daily
import com.jonas.hillitsweather.data.remote.Hourly
import com.jonas.hillitsweather.data.remote.WeatherDataDto
import com.jonas.hillitsweather.domain.weather.CompleteWeatherData
import com.jonas.hillitsweather.domain.weather.DailyWeatherData
import com.jonas.hillitsweather.domain.weather.WeatherData
import com.jonas.hillitsweather.utils.DateTimeHelper
import kotlin.math.roundToInt

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

private fun toFakeDailyWeatherForecast(daily: List<Daily>): List<DailyWeatherData> {
    return daily.map {
        val time = DateTimeHelper.toZonedDateTime(it.dt)
        val temperature = it.temp!!
        val windspeed = it.windSpeed
        val humidity = it.humidity
        val pressure = it.pressure
        val rainProbability = 0
        val description = it.weather[0].description!!
        val iconUrl = "${ICON_BASE_URL}01d@4x.png"

        DailyWeatherData(
            forecastedTime = time,
            temperatureDayCelsius = FAKE_TEMPERATURE,
            temperatureNightCelsius = FAKE_TEMPERATURE,
            temperatureMaxCelsius = FAKE_TEMPERATURE,
            temperatureMinCelsius = FAKE_TEMPERATURE,
            windspeed = windspeed,
            description = "Pure Sonne",
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
    val dailyForecast = toFakeDailyWeatherForecast(daily)
    val sunrise = DateTimeHelper.toZonedDateTime(current!!.sunrise)
    val sunset = DateTimeHelper.toZonedDateTime(current!!.sunset)

    return CompleteWeatherData(
        sunriseTime = sunrise,
        sunsetTime = sunset,
        current = currentWeather!!,
        hourForecast = hourForecast,
        dailyForecast = dailyForecast
    )
}
