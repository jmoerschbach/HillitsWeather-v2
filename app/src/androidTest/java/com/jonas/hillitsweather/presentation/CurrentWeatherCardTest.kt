package com.jonas.hillitsweather.presentation

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class CurrentWeatherCardTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun normalWeatherCard() {
        rule.setContent { 
           CurrentWeatherCard(WeatherState())
        }
        rule.onNode(hasText("Hildesheim")).assertExists()
    }
}