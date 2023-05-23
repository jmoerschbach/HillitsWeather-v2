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
import com.jonas.hillitsweather.domain.weather.WeatherData
import com.jonas.hillitsweather.utils.hourMinutesFormatter
import kotlin.math.roundToInt

@Composable
fun HourlyWeatherForecast(
    state: WeatherState,
    modifier: Modifier = Modifier
) {

    state.completeWeatherData?.let {
        val data = it.hourForecast
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
                    text = stringResource(id = R.string.next_hours),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    content = {
                        items(data) { hourForecast ->
                            HourlyWeatherDisplay(
                                weatherData = hourForecast,
                            )
                        }
                    })
            }
        }
    }


}

@Composable
private fun HourlyWeatherDisplay(
    weatherData: WeatherData,
    modifier: Modifier = Modifier,
) {
    val formattedTime = remember(weatherData) {
        weatherData.forecastedTime.format(hourMinutesFormatter)
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formattedTime,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(
                id = R.string.temperature_template,
                weatherData.temperatureCelsius.roundToInt()
            ),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        AsyncImage(model = weatherData.iconUrl, contentDescription = weatherData.description)
        IconValuePair(
            value = weatherData.rainProbability!!, unit = "%", icon = ImageVector.vectorResource(
                id = R.drawable.ic_drop
            )
        )

    }
}

