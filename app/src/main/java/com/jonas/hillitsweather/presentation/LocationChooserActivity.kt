package com.jonas.hillitsweather.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.jonas.hillitsweather.R
import com.jonas.hillitsweather.domain.weather.Location
import com.jonas.hillitsweather.ui.theme.HillitsWeatherTheme
import com.jonas.hillitsweather.utils.PickLocationContract.Companion.KEY_LOCATION
import org.koin.androidx.compose.getViewModel

class LocationChooserActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HillitsWeatherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SearchLocation(modifier = Modifier, this::onLocationClicked)
                }
            }
        }
    }

    private fun onLocationClicked(location: Location) {
        setResult(RESULT_OK, Intent().apply {
            putExtra(KEY_LOCATION, location)
        })
        finish()
    }
}

@Composable
private fun SearchLocation(
    modifier: Modifier,
    onItemClicked: (Location) -> Unit,
    viewModel: LocationViewModel = getViewModel()
) {
    val searchText by viewModel.searchText.collectAsState()
    val locations by viewModel.foundLocations2.collectAsState()
    //val isLoading by viewModel.isSearching.collectAsState()
    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = searchText,
            onValueChange = { viewModel.onSearchTextChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(
                        onClick = { viewModel.onSearchTextChanged("") },
                    ) {
                        Icon(
                            modifier = modifier.size(24.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.round_clear_24),
                            contentDescription = stringResource(id = R.string.clearTextField)
                        )
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
//        if(isLoading) {
//            PullRefreshIndicator(refreshing = isLoading , state = )
//        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(locations) { location ->
                Text(
                    text = location.formattedAdress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .clickable { onItemClicked(location) }
                )

            }
        }
    }

}

