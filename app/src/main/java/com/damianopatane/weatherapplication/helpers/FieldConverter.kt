package com.damianopatane.weatherapplication.helpers

import com.damianopatane.weatherapplication.domain.model.Daily
import com.damianopatane.weatherapplication.domain.model.Day
import com.damianopatane.weatherapplication.domain.model.Hourly
import java.math.RoundingMode
import java.text.DateFormat
import java.text.DateFormat.getTimeInstance
import java.text.SimpleDateFormat
import java.util.*

fun Double.decimalConversion(): Double = toBigDecimal().setScale(1, RoundingMode.HALF_UP).toDouble()

fun Double.removeDecimalConversion() : String = String.format("%.0f", this) + "°"

fun List<Hourly?>.sortByHour() : List<Hourly?> {
    this.forEach {
        val date = Date(it?.dt!!.toLong() * 1000)
        val sdf = SimpleDateFormat("HH")
        it.dt = sdf.format(date).toInt()
    }
    return this
}

fun Daily.sortByDay() : String {
    val cal = Calendar.getInstance()
    cal.time = Date(this.dt!!.toLong() * 1000)
    return Day.values()[(cal.get(Calendar.DAY_OF_WEEK) - 1)].toString()
}