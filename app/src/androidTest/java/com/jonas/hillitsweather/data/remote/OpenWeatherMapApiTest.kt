package com.jonas.hillitsweather.data.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jonas.hillitsweather.di.provideOpenWeatherMapApi
import kotlinx.coroutines.test.runTest
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OpenWeatherMapApiTest {


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

    @Test
    fun testValidInterface() {
        runTest {
            val interceptor = checkQueryParamsInterceptor("23", "5", "de", "metric")
            val builder = OkHttpClient.Builder()
            builder.interceptors().add(interceptor)
            val api = provideOpenWeatherMapApi(builder.build())


            api.getWeatherDataOneCall("23", "5", "de", "metric")
        }
    }

    private fun checkQueryParamsInterceptor(
        lat: String,
        lon: String,
        language: String,
        units: String
    ) = Interceptor { chain ->
        val request = chain.request()
        assertEquals("HTTP methods should match", "GET", request.method)
        assertEquals("a4c3f382fb9edbcbf500bfc77aa7a307", request.url.queryParameter("appId")!!)
        assertEquals(lat, request.url.queryParameter("lat")!!)
        assertEquals(lon, request.url.queryParameter("lon")!!)
        assertEquals(language, request.url.queryParameter("lang")!!)
        assertEquals(units, request.url.queryParameter("units")!!)
        assertEquals("alerts,minutely", request.url.queryParameter("exclude")!!)
        assertEquals("api.openweathermap.org", request.url.host)

        Response.Builder()
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("client config invalid")
            .body(onecallResponse.toResponseBody(null))
            .build()

    }
}