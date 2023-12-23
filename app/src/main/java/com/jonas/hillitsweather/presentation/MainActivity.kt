package com.jonas.hillitsweather.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.jonas.hillitsweather.R
import com.jonas.hillitsweather.ui.theme.HillitsWeatherTheme
import com.jonas.hillitsweather.utils.PickLocationContract
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherViewModel.loadWeather()
        setContent {
            HillitsWeatherTheme {
                AppUi()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AppUi(weatherViewModel: WeatherViewModel = getViewModel()) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = weatherViewModel.state.isLoading,
        onRefresh = {
            weatherViewModel.loadWeather()
        }
    )
    Box(Modifier.pullRefresh(pullRefreshState)) {
        Column(Modifier.fillMaxSize()) {
            TopAppBarCompose()
            WeatherUi()
        }
        PullRefreshIndicator(
            weatherViewModel.state.isLoading,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }

}

@Composable
fun WeatherUi(weatherViewModel: WeatherViewModel = getViewModel()) {
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
            DailyWeatherForecast(state = weatherViewModel.state)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarCompose(weatherViewModel: WeatherViewModel = getViewModel()) {
    val context = LocalContext.current

    val menuVisible = remember { mutableStateOf(false) }

    val aboutPopupVisible = remember { mutableStateOf(false) }

    val locationActivityLauncher =
        rememberLauncherForActivityResult(contract = PickLocationContract()) {
            it?.let {
                weatherViewModel.currentLocation = it
                weatherViewModel.loadWeather()
            }

        }

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
            IconButton(onClick = { locationActivityLauncher.launch(Unit) }) {
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
                    Text(text = "v23.05.23")

                    Row {
                        Text(text = "made with")
                        Spacer(modifier = Modifier.width(2.dp))
                        Image(
                            painter = painterResource(id = R.drawable.heart),
                            contentDescription = "love",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(text = "by Jonas")
                    }
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
