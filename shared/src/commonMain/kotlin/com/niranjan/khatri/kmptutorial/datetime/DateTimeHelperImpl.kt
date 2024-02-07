package com.niranjan.khatri.kmptutorial.datetime

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DateTimeHelperImpl : DateTimeHelper{

    override fun getTimeZoneStrings(): List<String> {
        return TimeZone.availableZoneIds.sorted()
    }

    fun formatDateTime(dateTime: LocalDateTime): String{
        // StringBuilder is used to build the string piece by piece.
        val stringBuilder = StringBuilder()
        val minute = dateTime.minute
        var hour = dateTime.hour % 12
        val amPm = if (dateTime.hour < 12) "am" else "pm"
        stringBuilder.append(hour.toString())
        stringBuilder.append(":")
        if (minute < 10) stringBuilder.append('0')
        stringBuilder.append(minute.toString())
        stringBuilder.append(amPm)
        return stringBuilder.toString()
    }
    override fun getCurrentTime(): String {
        val now : Instant = Clock.System.now()
        val dateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
        return formatDateTime(dateTime)
    }

    override fun getCurrentTimeZoneId() = TimeZone.currentSystemDefault().toString()


    override fun getDate(timezoneId: String): String {
        val timezone = TimeZone.of(timezoneId)
        val now = Clock.System.now()
        val dateTime = now.toLocalDateTime(timezone)
        return "${dateTime.date}"
    }

}