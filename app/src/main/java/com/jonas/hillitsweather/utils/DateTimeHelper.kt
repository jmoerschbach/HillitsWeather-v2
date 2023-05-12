package com.jonas.hillitsweather.utils

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DateTimeHelper {

    private val dayFormatter = DateTimeFormatter.ofPattern("E").withLocale(Locale.GERMAN)
    private val dayMonthFormatter =
        DateTimeFormatter.ofPattern("dd.MM.").withLocale(Locale.GERMAN)

    fun toZonedDateTime(epoch: Long): ZonedDateTime {
        return ZonedDateTime.ofInstant(
            Instant.ofEpochSecond(epoch),
            ZoneId.systemDefault()
        )
    }

    fun toDayString(date: ZonedDateTime): String = dayFormatter.format(date)
    fun toDayMonthString(date: ZonedDateTime): String = dayMonthFormatter.format(date)

}
