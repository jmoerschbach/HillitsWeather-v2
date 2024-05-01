package com.jonas.hillitsweather.presentation

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import com.jonas.hillitsweather.domain.weather.CompleteWeatherData
import com.jonas.hillitsweather.domain.weather.WeatherData
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class CurrentWeatherCardTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun normalWeatherCard() {
        val mockedCompleteWeatherData = mockk<CompleteWeatherData>(relaxed = true)
        val mockedWeatherData = mockk<WeatherData>(relaxed = true)
        every { mockedCompleteWeatherData.city } returns  "Hildesheim"
        every { mockedCompleteWeatherData.current } returns mockedWeatherData
        rule.setContent {
            CurrentWeatherCard(
                WeatherState(
                    isLoading = false,
                    error = null,
                    completeWeatherData = mockedCompleteWeatherData
                )
            )
        }
        rule.onNode(hasText("Hildesheim")).assertExists()
    }
}