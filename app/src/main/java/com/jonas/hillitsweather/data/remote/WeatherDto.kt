package com.jonas.hillitsweather.data.remote

import androidx.annotation.Keep
import com.squareup.moshi.Json
@Keep
class WeatherDataDto {

    @field:Json(name = "lon")
    var lon: Float = 0.toFloat()

    @field:Json(name = "lat")
    var lat: Float = 0.toFloat()

    @field:Json(name = "timezone")
    var timezone: String? = null

    @field:Json(name = "current")
    var current: Current? = null

    @field:Json(name = "hourly")
    var hourly = listOf<Hourly>()
//
//    @field:Json(name = "daily")
//    var daily = ArrayList<Daily>()
}
@Keep
class Current {
    @field:Json(name = "dt")
    var dt: Long = 0

    @field:Json(name = "sunrise")
    var sunrise: Long = 0

    @field:Json(name = "sunset")
    var sunset: Long = 0

    @field:Json(name = "temp")
    var temp: Float = 0.toFloat()

    @field:Json(name = "weather")
    var weather = listOf<Weather>()

    @field:Json(name = "clouds")
    var clouds: Float = 0.toFloat()

    @field:Json(name = "wind_speed")
    var windSpeed: Float = 0.toFloat()

    @field:Json(name = "wind_deg")
    var windDegree: Float = 0.toFloat()

    @field:Json(name = "humidity")
    var humidity: Int = 0

    @field:Json(name = "pressure")
    var pressure: Int = 0
}
@Keep
class Hourly {
    @field:Json(name = "dt")
    var dt: Long = 0

    @field:Json(name = "temp")
    var temp: Float = 0.toFloat()

    @field:Json(name = "feels_like")
    var feelsLike: Float = 0.toFloat()

    @field:Json(name = "weather")
    var weather = listOf<Weather>()

    @field:Json(name = "clouds")
    var clouds: Int = 0

    @field:Json(name = "wind_speed")
    var windSpeed: Float = 0.toFloat()

    @field:Json(name = "pop")
    var rainProbability: Float = 0.toFloat()

    @field:Json(name = "wind_deg")
    var windDegree: Float = 0.toFloat()

    @field:Json(name = "humidity")
    var humidity: Int = 0

    @field:Json(name = "pressure")
    var pressure: Int = 0
}

/*
class Daily {
    @field:Json(name = "dt")
    var dt: Long = 0

    @field:Json(name = "sunrise")
    var sunrise: Long = 0

    @field:Json(name = "sunset")
    var sunset: Long = 0

    @field:Json(name = "temp")
    var temp: Temperature? = null

    @field:Json(name = "wind_speed")
    var windSpeed: Float = 0.toFloat()

    @field:Json(name = "wind_deg")
    var windDegree: Float = 0.toFloat()

    @field:Json(name = "clouds")
    var clouds: Int = 0

    @field:Json(name = "pop")
    var rainProbability: Float = 0.toFloat()

    @field:Json(name = "weather")
    var weather = listOf<Weather>()
}

class Temperature {
    @field:Json(name = "day")
    var day: Float = 0.toFloat()

    @field:Json(name = "min")
    var min: Float = 0.toFloat()

    @field:Json(name = "max")
    var max: Float = 0.toFloat()

    @field:Json(name = "night")
    var night: Float = 0.toFloat()

    @field:Json(name = "eve")
    var evening: Float = 0.toFloat()

    @field:Json(name = "morn")
    var morning: Float = 0.toFloat()

}
*/
@Keep
class Weather {
    @field:Json(name = "id")
    var id: Int = 0

    @field:Json(name = "main")
    var main: String? = null

    @field:Json(name = "description")
    var description: String? = null

    @field:Json(name = "icon")
    var icon: String? = null
}
/*
class HourlyForecast {


    @field:Json(name = "list")
    var hourly = ArrayList<Hour>()

}

class Hour {
    @field:Json(name = "dt")
    var dt: Long = 0

    @field:Json(name = "weather")
    var weather = listOf<Weather>()

    @field:Json(name = "main")
    var main: Main? = null

    @field:Json(name = "pop")
    var rainProbability: Float = 0.toFloat()

    @field:Json(name = "dt_txt")
    var dtText: String? = null


}

class Main {

    @field:Json(name = "temp")
    var temperature: Float = 0.toFloat()

    @field:Json(name = "temp_min")
    var temperatureMin: Float = 0.toFloat()

    @field:Json(name = "temp_max")
    var temperatureMax: Float = 0.toFloat()
}


*/







