package com.damianopatane.weatherapplication.data.source.local.dao

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class DailyDao (
    @SerializedName("dt") var dt : Int? = 0,
    @SerializedName("sunrise") var sunrise : Int? = 0,
    @SerializedName("sunset") var sunset : Int? = 0,
    @SerializedName("temp") var temp : TempDao? = null,
    @SerializedName("feels_like") var feels_like : FeelsLikeDao? = null,
    @SerializedName("pressure") var pressure : Int? = 0,
    @SerializedName("humidity") var humidity : Int? = 0,
    @SerializedName("dew_point") var dew_point : Double? = null,
    @SerializedName("wind_speed") var wind_speed : Double? = null,
    @SerializedName("wind_deg") var wind_deg : Int? = 0,
    @SerializedName("weather") var weather : RealmList<WeatherDao>? = null,
    @SerializedName("clouds") var clouds : Int? = 0,
    @SerializedName("rain") var rain : Double? = null,
    @SerializedName("uvi") var uvi : Double? = null
) : RealmObject()