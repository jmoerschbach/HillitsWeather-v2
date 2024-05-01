package com.jonas.hillitsweather.data.repository

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jonas.hillitsweather.domain.weather.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val USER_PREFERENCES_NAME = "user_preferences"

private val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

data class UserPreferences(val currentLocation: Location?, val isRealMode: Boolean?)
class UserPreferenceRepository(private val context: Context) {

    private object PreferenceKeys {
        val CURRENT_LOCATION_LAT = doublePreferencesKey("current_location_lat")
        val CURRENT_LOCATION_LON = doublePreferencesKey("current_location_lon")
        val CURRENT_LOCATION_CITY = stringPreferencesKey("current_location_city")
        val CURRENT_LOCATION_ADDRESS = stringPreferencesKey("current_location_formattedAddress")
        val CURRENT_MODE = booleanPreferencesKey("current_mode_real")
    }

    val userPreferencesFlow: Flow<UserPreferences> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences -> mapUserPreferences(preferences) }

    suspend fun storeCurrentLocation(currentLocation: Location) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.CURRENT_LOCATION_LON] = currentLocation.lon.toDouble()
            preferences[PreferenceKeys.CURRENT_LOCATION_LAT] = currentLocation.lat.toDouble()
            preferences[PreferenceKeys.CURRENT_LOCATION_CITY] = currentLocation.city
            preferences[PreferenceKeys.CURRENT_LOCATION_ADDRESS] = currentLocation.formattedAdress
        }
    }

    suspend fun storeCurrentMode(isRealMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.CURRENT_MODE] = isRealMode
        }
    }

    @Suppress("ComplexCondition")
    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val latitude = preferences[PreferenceKeys.CURRENT_LOCATION_LAT]
        val longitude = preferences[PreferenceKeys.CURRENT_LOCATION_LON]
        val city = preferences[PreferenceKeys.CURRENT_LOCATION_CITY]
        val formattedAddress = preferences[PreferenceKeys.CURRENT_LOCATION_ADDRESS]

        val location =
            if (latitude != null && longitude != null && city != null && formattedAddress != null) {
                Location(
                    longitude.toFloat(),
                    latitude.toFloat(),
                    city,
                    formattedAddress
                )

            } else {
                null
            }
        val mode = preferences[PreferenceKeys.CURRENT_MODE]

        return UserPreferences(location, mode)
    }

}

