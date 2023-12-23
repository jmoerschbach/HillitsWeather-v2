package com.jonas.hillitsweather.data.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jonas.hillitsweather.di.provideGeoApi
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
class GeoapifyApiTest {


    companion object {
        private lateinit var autocompleteResponse: String

        @JvmStatic
        @BeforeClass
        fun setup() {
            val instrumentationContext = InstrumentationRegistry.getInstrumentation().context
            autocompleteResponse =
                instrumentationContext.assets.open("autocomplete.json").bufferedReader()
                    .readText()
        }
    }

    @Test
    fun testValidInterface() {
        runTest {
            val interceptor = checkQueryParamsInterceptor("Hild", "de", "json",)
            val builder = OkHttpClient.Builder()
            builder.interceptors().add(interceptor)
            val api = provideGeoApi(builder.build())


            api.getMatchingLocations("Hild", "de", "json",)
        }
    }

    private fun checkQueryParamsInterceptor(
        addresspart: String,
        language: String,
        format: String
    ) = Interceptor { chain ->
        val request = chain.request()
        assertEquals("HTTP methods should match", "GET", request.method)
        assertEquals("358042b817114819a29c1e689d8fd2b6", request.url.queryParameter("apiKey")!!)
        assertEquals(addresspart, request.url.queryParameter("text")!!)
        assertEquals(language, request.url.queryParameter("lang")!!)
        assertEquals(format, request.url.queryParameter("format")!!)
        assertEquals("api.geoapify.com", request.url.host)

        Response.Builder()
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("")
            .body(autocompleteResponse.toResponseBody(null))
            .build()

    }
}