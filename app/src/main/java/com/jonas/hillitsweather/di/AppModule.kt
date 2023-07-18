package com.jonas.hillitsweather.di

import com.jonas.hillitsweather.data.remote.GeoapifyApi
import com.jonas.hillitsweather.data.remote.OpenWeatherMapApi
import com.jonas.hillitsweather.data.repository.GeoapifyRepository
import com.jonas.hillitsweather.data.repository.OpenWeatherMapRepository
import com.jonas.hillitsweather.domain.repository.LocationRepository
import com.jonas.hillitsweather.domain.repository.WeatherRepository
import com.jonas.hillitsweather.presentation.WeatherViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


fun provideOpenWeatherMapApi(client: OkHttpClient = OkHttpClient()): OpenWeatherMapApi =
    Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()
        .create(OpenWeatherMapApi::class.java)

fun provideGeoApi(client: OkHttpClient = OkHttpClient()): GeoapifyApi =
    Retrofit.Builder()
        .baseUrl("https://api.geoapify.com/v1/geocode/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()
        .create(GeoapifyApi::class.java)


val appModule = module {
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

    single { provideOpenWeatherMapApi(client) }
    single { provideGeoApi(client) }
    single<WeatherRepository> { OpenWeatherMapRepository(get()) }
    single<LocationRepository> { GeoapifyRepository(get()) }
    viewModel { WeatherViewModel(get(), get()) }
}
