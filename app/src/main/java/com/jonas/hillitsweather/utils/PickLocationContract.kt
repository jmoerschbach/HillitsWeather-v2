package com.jonas.hillitsweather.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.jonas.hillitsweather.domain.weather.Location
import com.jonas.hillitsweather.presentation.LocationChooserActivity

class PickLocationContract : ActivityResultContract<Unit, Location?>() {
    override fun createIntent(context: Context, input: Unit) =
        Intent(context, LocationChooserActivity::class.java)


    override fun parseResult(resultCode: Int, intent: Intent?): Location? {
        if(resultCode!=Activity.RESULT_OK) {
            return null
        }
       return intent?.let {
            val lon = it.getFloatExtra("lon", 0f)
            val lat = it.getFloatExtra("lat", 0f)
            val city = it.getStringExtra("city").orEmpty()
            val formattedAddress = it.getStringExtra("formattedAddress").orEmpty()
            Location(lon, lat, city, formattedAddress)
        }
    }
}