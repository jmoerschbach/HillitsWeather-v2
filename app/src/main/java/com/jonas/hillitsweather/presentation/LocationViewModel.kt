package com.jonas.hillitsweather.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonas.hillitsweather.domain.repository.LocationRepository
import com.jonas.hillitsweather.domain.weather.Location
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val TAG = "LocationViewModel"

class LocationViewModel(private val locationRepo: LocationRepository) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    val _foundLocations = MutableStateFlow(listOf<Location>())

    val foundLocations2 = _foundLocations.asStateFlow()


    @OptIn(FlowPreview::class)
    val foundLocations = searchText
        .debounce(500L)
//        .map { onSearchTextChanged(it) }
        .combine(_foundLocations) { text, locations ->
            Log.d(TAG, "api call?")
            onSearchTextChanged(text)
            _foundLocations
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000), _foundLocations.value
        )

    fun onSearchTextChange(cityNamePart: String) {
        Log.d(TAG, "search text changed to $cityNamePart")
        _searchText.value = cityNamePart
    }

    fun onSearchTextChanged(cityNamePart: String) {
        viewModelScope.launch {

            Log.d("Jonas", "fetching location")
            _foundLocations.value = listOf()
            _searchText.value = cityNamePart
            _isSearching.value = true
            val result = locationRepo.getPossibleLocations(cityNamePart)
            _isSearching.value = false
            result.data?.let {
                if (it.isNotEmpty()) {
                    _foundLocations.value = it
                    val x = it[0]
                    Log.d("Jonas", "${x.lon} ${x.lon} ${x.formattedAdress}")
                }
            }
        }
    }
}
