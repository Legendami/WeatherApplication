package com.damianopatane.weatherapplication.data.source.local.dao

import com.damianopatane.weatherapplication.domain.model.CityWeather
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class CityDao (
    @SerializedName("coordDao") var coordDao: CoordDao? = null,
    @SerializedName("country") var country : String? = null,
    var cityWeatherDao: CityWeatherDao? = null,
    @PrimaryKey
    @SerializedName("id") var id : Int? = 0,
    @SerializedName("name") var name : String? = null,
    @SerializedName("state") var state : String? = null
): RealmObject()