package com.jonas.hillitsweather.presentation

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import com.jonas.hillitsweather.domain.weather.CompleteWeatherData
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class CurrentWeatherCardTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun normalWeatherCard() {
        val x = mockk<CompleteWeatherData>(relaxed = true)
        rule.setContent {
            CurrentWeatherCard(
                WeatherState(
                    isLoading = false,
                    error = null,
                    completeWeatherData = x
                )
            )
        }
        rule.onNode(hasText("Hildesheim")).assertExists()
    }
}