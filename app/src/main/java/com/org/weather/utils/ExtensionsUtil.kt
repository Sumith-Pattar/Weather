package com.org.weather.utils

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/*Converts double type  temperature from kelvin to celsius*/
fun Double.kelvinToCelsius(): String {
    return (this - 273.15).toInt().toString().plus("\u00B0 C")
}

/**/
fun Long.unixTimeDateStamp(): String {
    try {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this * 1000
        val outputDateFormat = SimpleDateFormat("EEE, dd MMM,yyyy,  hh:mm a", Locale.ENGLISH)
        outputDateFormat.timeZone = TimeZone.getDefault()
        return outputDateFormat.format(calendar.time)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return this.toString()
}

fun Int.humidityPercentage():String{
    return this.toString().plus("%")
}

fun Double.pressureUnit():String{
    return this.toString().plus(" hPa")
}

fun Double.windSpeedUnit():String{
    return this.toString().plus(" km/h")
}

