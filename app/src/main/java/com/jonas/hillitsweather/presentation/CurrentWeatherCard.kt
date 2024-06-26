package com.jonas.hillitsweather.presentation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jonas.hillitsweather.R
import com.jonas.hillitsweather.domain.weather.CompleteWeatherData
import com.jonas.hillitsweather.domain.weather.WeatherData
import com.jonas.hillitsweather.utils.hourMinutesFormatter
import kotlin.math.roundToInt


@Composable
fun CurrentWeatherCard(
    state: WeatherState,
//    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    state.completeWeatherData?.let { completeWeatherData ->
        val currentWeatherData = completeWeatherData.current
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Heading(currentWeatherData, completeWeatherData)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = completeWeatherData.city, Modifier.align(Alignment.Start), fontSize = 30.sp)
                MainInfo(currentWeatherData)
                Spacer(modifier = Modifier.height(16.dp))
                Footer(currentWeatherData = currentWeatherData)
            }
        }
    }
}

@Composable
private fun MainInfo(currentWeatherData: WeatherData) {
    AsyncImage(
        model = currentWeatherData.iconUrl,
        contentDescription = currentWeatherData.description,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .height(128.dp)
            .width(128.dp)
    )
//                Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = stringResource(
            id = R.string.temperature_template,
            currentWeatherData.temperatureCelsius.roundToInt()
        ),
        fontSize = 50.sp,
    )
    Text(
        text = currentWeatherData.description,
        fontSize = 20.sp,
    )
}

@Composable
private fun Heading(
    currentWeatherData: WeatherData,
    completeWeatherData: CompleteWeatherData
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${stringResource(id = R.string.updated)} ${
                currentWeatherData.forecastedTime.format(hourMinutesFormatter)
            }"

        )
        Row {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.sunrise),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = completeWeatherData.sunriseTime.format(hourMinutesFormatter),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.sunset),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = completeWeatherData.sunsetTime.format(hourMinutesFormatter),
            )
        }
    }
}

@Composable
private fun Footer(
    currentWeatherData: WeatherData
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconValuePair(
            value = currentWeatherData.pressure,
            unit = "hpa",
            icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
            textStyle = TextStyle()
        )
        IconValuePair(
            value = currentWeatherData.humidity,
            unit = "%",
            icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
            textStyle = TextStyle()
        )
        IconValuePair(
            value = currentWeatherData.windspeed.roundToInt(),
            unit = "km/h",
            icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
            textStyle = TextStyle()
        )
    }
}

@Composable
fun IconValuePair(
    value: Int,
    unit: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),
    iconTint: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "$value$unit",
            style = textStyle
        )
    }
}
