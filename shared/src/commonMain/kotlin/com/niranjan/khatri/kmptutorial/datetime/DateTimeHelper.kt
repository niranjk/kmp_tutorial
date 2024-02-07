package com.niranjan.khatri.kmptutorial.datetime

interface DateTimeHelper {
    // Return the current formatted Time
    fun getCurrentTime(): String

    // Return the current time zone Id
    fun getCurrentTimeZoneId(): String

    // Return the formatted date for the given time zone
    fun getDate(timezoneId: String): String

    // Returns all time zone strings
    fun getTimeZoneStrings(): List<String>
}