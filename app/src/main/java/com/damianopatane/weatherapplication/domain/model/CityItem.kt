package com.damianopatane.weatherapplication.domain.model

import java.io.Serializable

data class CityItem(
    var id : Int,
    var name : String,
    var state : String,
    var country : String,
    var coord : CoordVal,
    var cityWeather : CityWeather
) : Serializable