package com.damianopatane.weatherapplication.data.source.local.dao

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class HourlyDao (
    @SerializedName("dt") var dt : Int? = 0,
    @SerializedName("temp") var temp : Double? = null,
    @SerializedName("feels_like") var feels_like : Double? = null,
    @SerializedName("pressure") var pressure : Int? = 0,
    @SerializedName("humidity") var humidity : Int? = 0,
    @SerializedName("dew_point") var dew_point : Double? = null,
    @SerializedName("clouds") var clouds : Int? = 0,
    @SerializedName("wind_speed") var wind_speed : Double? = null,
    @SerializedName("wind_deg") var wind_deg : Int? = 0,
    @SerializedName("weather") var weather : RealmList<WeatherDao>? = null
) : RealmObject()