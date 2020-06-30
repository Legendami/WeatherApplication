package com.damianopatane.weatherapplication.data.source.local.dao

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class Feels_likeDao(
    @SerializedName("day") var day : Double? = null,
    @SerializedName("night") var night : Double? = null,
    @SerializedName("eve") var eve : Double? = null,
    @SerializedName("morn") var morn : Double? = null
) : RealmObject()