package com.damianopatane.weatherapplication.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CityWeather (
    var lat : Double,
    var lon : Double,
    var timezone : String,
    var timezone_offset : Int,
    var current : Current,
    var hourly : List<Hourly>,
    var daily : List<Daily>
) : Serializable