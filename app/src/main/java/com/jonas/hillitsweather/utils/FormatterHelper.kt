package com.jonas.hillitsweather.utils

import java.time.format.DateTimeFormatter

fun Float.formatTemperature() = "%.1f".format(this)
val hourMinutesFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")