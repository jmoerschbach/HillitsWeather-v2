package com.jonas.hillitsweather.di

import com.jonas.hillitsweather.data.remote.OpenWeatherMapApi
import com.jonas.hillitsweather.data.repository.OpenWeatherMapRepository
import com.jonas.hillitsweather.domain.repository.WeatherRepository
import com.jonas.hillitsweather.presentation.WeatherViewModel
import okhttp3.OkHttpClient
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


val appModule = module {
    single { provideOpenWeatherMapApi() }
    single<WeatherRepository> { OpenWeatherMapRepository(get()) }
    viewModel { WeatherViewModel(get()) }
}
