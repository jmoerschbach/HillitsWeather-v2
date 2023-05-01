package com.jonas.hillitsweather.presentation

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.jonas.hillitsweather.BuildConfig
import com.jonas.hillitsweather.R
import com.jonas.hillitsweather.ui.theme.HillitsWeatherTheme
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherViewModel.loadWeather()
        setContent {
            HillitsWeatherTheme {
                Column(
                    modifier = Modifier
//                        .verticalScroll(rememberScrollState())
                ) {
                    TopAppBarCompose()
                    WeatherUi()
                }
            }
        }
    }

    @Composable
    private fun WeatherUi() {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                CurrentWeatherCard(state = weatherViewModel.state)
                HourlyWeatherForecast(state = weatherViewModel.state)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarCompose(weatherViewModel: WeatherViewModel = getViewModel()) {
    val context = LocalContext.current

    val menuVisible = remember { mutableStateOf(false) }

    val aboutPopupVisible = remember { mutableStateOf(false) }

    val sun = if (weatherViewModel.weatherModeIsHillit) ImageVector.vectorResource(
        id = R.drawable.sun_own_filled
    ) else ImageVector.vectorResource(id = R.drawable.sun_own)

    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
            IconButton(onClick = {
                weatherViewModel.toggleMode()
            }) {
                Icon(
                    imageVector = sun,
                    contentDescription = stringResource(id = R.string.change_mode),
                    modifier = Modifier.size(30.dp),
                    tint = if (weatherViewModel.weatherModeIsHillit) Color.Yellow else LocalContentColor.current
                )
            }
            IconButton(onClick = { makeToast(context, "Sorry, not yet supported ;)") }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search)
                )
            }

            IconButton(onClick = { menuVisible.value = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = null)
            }
            DropdownMenu(
                expanded = menuVisible.value,
                onDismissRequest = { menuVisible.value = false }) {
                DropdownMenuItem(text = {
                    Text(text = stringResource(id = R.string.settings))
                }, onClick = { makeToast(context, "Sorry, not yet supported ;)") })
                DropdownMenuItem(text = {
                    Text(text = stringResource(id = R.string.about))
                }, onClick = {
                    aboutPopupVisible.value = true
                    menuVisible.value = false
                })
            }
        })

    if (aboutPopupVisible.value) {
        AboutPopup(onDismissRequest = { aboutPopupVisible.value = false })
    }
}

@Composable
private fun AboutPopup(onDismissRequest: () -> Unit) {
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = onDismissRequest
    ) {
        Surface(shape = RoundedCornerShape(8.dp)) {
            Column(
                modifier = Modifier
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.sun_own_filled),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(id = R.string.app_name), fontSize = 30.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = stringResource(id = R.string.about_description))

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.width(300.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "v${BuildConfig.VERSION_NAME}")
                    Text(text = "made by Jonas")
                }
            }
        }
    }
}

fun makeToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HillitsWeatherTheme {

    }
}