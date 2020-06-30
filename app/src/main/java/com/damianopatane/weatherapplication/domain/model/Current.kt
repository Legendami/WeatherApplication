package com.damianopatane.weatherapplication.domain.model

import io.realm.RealmList
import java.io.Serializable

data class Current (
    var dt : Int,
    var sunrise : Int,
    var sunset : Int,
    var temp : Double,
    var feels_like : Double,
    var pressure : Int,
    var humidity : Int,
    var dew_point : Double,
    var uvi : Double,
    var clouds : Int,
    var visibility : Int,
    var wind_speed : Double,
    var wind_deg : Int,
    var weather : List<Weather>
) : Serializable