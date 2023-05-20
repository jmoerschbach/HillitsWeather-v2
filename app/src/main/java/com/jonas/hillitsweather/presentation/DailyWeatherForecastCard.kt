package com.jonas.hillitsweather.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jonas.hillitsweather.R
import com.jonas.hillitsweather.domain.weather.DailyWeatherData
import com.jonas.hillitsweather.utils.DateTimeHelper
import com.jonas.hillitsweather.utils.formatTemperature

@Composable
fun DailyWeatherForecast(
    state: WeatherState,
    modifier: Modifier = Modifier
) {

    state.completeWeatherData?.let {
        val data = it.dailyForecast
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.padding(16.dp)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.next_days),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    content = {
                        items(data) { dailyForecast ->
                            DailyWeatherDisplay(
                                weatherData = dailyForecast,
                            )
                        }
                    })
            }
        }
    }


}

@Composable
private fun DailyWeatherDisplay(
    weatherData: DailyWeatherData,
    modifier: Modifier = Modifier,
) {
    val weekDay = remember(weatherData) {
        DateTimeHelper.toDayString(weatherData.forecastedTime)
    }
    val date = remember(weatherData) {
        DateTimeHelper.toDayMonthString(weatherData.forecastedTime)
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = weekDay,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = date,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(
                id = R.string.temperature_template,
                weatherData.temperatureDayCelsius.formatTemperature()
            ),
            fontWeight = FontWeight.Bold
        )
        AsyncImage(model = weatherData.iconUrl, contentDescription = weatherData.description)
        IconValuePair(
            value = weatherData.rainProbability, unit = "%", icon = ImageVector.vectorResource(
                id = R.drawable.ic_drop
            )
        )

    }
}

