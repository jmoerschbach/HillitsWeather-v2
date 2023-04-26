package com.jonas.hillitsweather.presentation


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
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
import com.jonas.hillitsweather.ui.theme.DeepBlue
import com.jonas.hillitsweather.utils.formatTemperature
import com.jonas.hillitsweather.utils.hourMinutesFormatter
import java.time.format.DateTimeFormatter
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
                Text(text = "Hildesheim", Modifier.align(Alignment.Start), fontSize = 30.sp)
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
            currentWeatherData.temperatureCelsius.formatTemperature()
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
            Text(
                text = "A: ${completeWeatherData.sunriseTime.format(hourMinutesFormatter)}",
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "U: ${completeWeatherData.sunsetTime.format(hourMinutesFormatter)}",
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
    iconTint: Color = /*if (isSystemInDarkTheme())*/ MaterialTheme.colorScheme.onSurface/* else Color.Black*/
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
