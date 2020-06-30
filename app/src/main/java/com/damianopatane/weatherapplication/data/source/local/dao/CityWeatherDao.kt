package com.damianopatane.weatherapplication.data.source.local.dao

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class CityWeatherDao (
    @SerializedName("lat") var lat : Double? = null,
    @SerializedName("lon") var lon : Double? = null,
    @SerializedName("timezone") var timezone : String? = null,
    @SerializedName("timezone_offset") var timezone_offset : Int? = 0,
    @SerializedName("current") var current : CurrentDao? = null,
    @SerializedName("hourly") var hourly : RealmList<HourlyDao>? = null,
    @SerializedName("daily") var daily : RealmList<DailyDao>? = null
) : RealmObject()