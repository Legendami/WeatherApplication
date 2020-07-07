package com.damianopatane.weatherapplication.data.source.local.dao

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class WeatherDao (
    @SerializedName("description") var description : String? = null,
    @SerializedName("icon") var icon : String? = null,
    @SerializedName("id") var id : Int? = 0,
    @SerializedName("main") var main : String? = null
) : RealmObject()