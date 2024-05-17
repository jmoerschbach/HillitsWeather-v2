package com.jonas.hillitsweather.data.mappers

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jonas.hillitsweather.data.MockNetworkInterceptor
import com.jonas.hillitsweather.data.remote.OpenWeatherMapApi
import com.jonas.hillitsweather.di.provideOpenWeatherMapApi
import com.jonas.hillitsweather.domain.weather.CompleteWeatherData
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OpenWeatherMapMapperTest {


    companion object {
        private lateinit var onecallResponse: String

        @JvmStatic
        @BeforeClass
        fun setup() {
            val instrumentationContext = InstrumentationRegistry.getInstrumentation().context
            onecallResponse =
                instrumentationContext.assets.open("onecallResponse.json").bufferedReader()
                    .readText()
        }
    }

    private lateinit var completeWeatherData: CompleteWeatherData

    @Before
    fun init() {
        val api = createApi(
            interceptor = MockNetworkInterceptor().mockOneCall(
                { onecallResponse },
                200
            )
        )
        runTest {
            completeWeatherData = api.getWeatherDataOneCall("51.1955", "7.0085", "de", "metric")
                .toCompleteWeatherData()
        }
    }

    private fun createApi(interceptor: MockNetworkInterceptor): OpenWeatherMapApi {
        val builder = OkHttpClient.Builder()
        builder.interceptors().add(interceptor)
        return provideOpenWeatherMapApi(builder.build())
    }

    @Test
    fun testWeatherDataExists() {
        assertEquals(24, completeWeatherData.hourForecast.size)
        assertEquals(8, completeWeatherData.dailyForecast.size)
        assertNotNull(completeWeatherData.current)
    }

@Test
fun testDailyForecastData() {
    val day = completeWeatherData.dailyForecast[0]
    assertEquals(100, day.rainProbability)
    assertEquals(1011, day.pressure)
    assertEquals(5.97f, day.windspeed)
    assertEquals(40, day.humidity)
    assertEquals(17.75f, day.temperatureMinCelsius, 0.0f)
    assertEquals(30.84f, day.temperatureMaxCelsius, 0.0f)
    assertEquals(28.87f, day.temperatureDayCelsius, 0.0f)
    assertEquals(21.73f, day.temperatureNightCelsius,0.0f)
    assertEquals(26.21f, day.temperatureEveningCelsius,0.0f)
    assertEquals(17.75f, day.temperatureMorningCelsius,0.0f)

    assertEquals("Mäßiger Regen", day.description)
    assertEquals("2023-06-20T13:00+02:00[Europe/Berlin]", day.forecastedTime.toString())
}
    @Test
    fun testHourlyForecastData() {
        val hour = completeWeatherData.hourForecast[0]
        assertEquals(100, hour.rainProbability)
        assertEquals(1011, hour.pressure)
        assertEquals(5.97f, hour.windspeed)
        assertEquals(40, hour.humidity)
        assertEquals(17.75f, hour.temperatureCelsius, 0.0f)
        assertEquals("Mäßiger Regen", hour.description)
        assertEquals("2023-06-20T13:00+02:00[Europe/Berlin]", hour.forecastedTime.toString())
    }

}