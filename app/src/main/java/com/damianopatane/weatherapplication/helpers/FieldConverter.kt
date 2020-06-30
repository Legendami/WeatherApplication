package com.damianopatane.weatherapplication.helpers

import com.damianopatane.weatherapplication.domain.model.Daily
import com.damianopatane.weatherapplication.domain.model.Day
import com.damianopatane.weatherapplication.domain.model.Hourly
import java.math.RoundingMode
import java.text.DateFormat
import java.text.DateFormat.getTimeInstance
import java.text.SimpleDateFormat
import java.util.*

class FieldConverter {
    companion object {
        private var INSTANCE: FieldConverter? = null
        fun getInstance(): FieldConverter {
            if (INSTANCE == null) {
                INSTANCE = FieldConverter()
            }
            return INSTANCE!!
        }
    }

    fun decimalConversion(temp : Double) : Double {
        var newTemp = temp.toBigDecimal().setScale(1, RoundingMode.HALF_UP).toDouble()
        return newTemp
    }

    fun removeDecimalConversion(temp : Double) : String {
        var roundedTemp = String.format("%.0f", temp) + "°"
        return roundedTemp
    }

    fun sortByHour(hours : List<Hourly>) : List<Hourly> {
        val cal = Calendar.getInstance()
        hours.forEach {
            val date = Date(it.dt.toLong() * 1000)
            val sdf = SimpleDateFormat("HH")
            it.dt = sdf.format(date).toInt()
        }
        return hours
    }

    fun sortByDay(daily : Daily) : String {
        val time = getTimeInstance(DateFormat.SHORT)
        val cal = Calendar.getInstance()
        cal.time = Date(daily.dt.toLong() * 1000)
        return Day.values()[(cal.get(Calendar.DAY_OF_WEEK) - 1)].toString()
    }
}