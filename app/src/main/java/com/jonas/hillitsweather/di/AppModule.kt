package com.jonas.hillitsweather.di

import com.jonas.hillitsweather.data.remote.RealWeatherApi
import com.jonas.hillitsweather.data.repository.WeatherRepositoryImpl
import com.jonas.hillitsweather.domain.repository.WeatherRepository
import com.jonas.hillitsweather.presentation.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.get
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create


private fun provideRealWeatherApi(): RealWeatherApi = Retrofit.Builder()
    .baseUrl("https://api.openweathermap.org/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()
    .create()

val appModule = module {
    single { provideRealWeatherApi() }
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
    viewModel { WeatherViewModel(get()) }
}
