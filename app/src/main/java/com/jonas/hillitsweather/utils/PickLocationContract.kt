package com.jonas.hillitsweather.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION
import androidx.activity.result.contract.ActivityResultContract
import com.jonas.hillitsweather.domain.weather.Location
import com.jonas.hillitsweather.presentation.LocationChooserActivity

class PickLocationContract : ActivityResultContract<Unit, Location?>() {
    companion object {
        const val KEY_LOCATION = "location"
    }
    override fun createIntent(context: Context, input: Unit) =
        Intent(context, LocationChooserActivity::class.java)


    override fun parseResult(resultCode: Int, intent: Intent?): Location? {
        if (resultCode != Activity.RESULT_OK || intent == null) {
            return null
        }

        val location = if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(KEY_LOCATION, Location::class.java)
        } else {
            intent.getParcelableExtra(KEY_LOCATION)
        }
        return location
    }
}
