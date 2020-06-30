package com.damianopatane.weatherapplication.data.source.local.dao

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class CoordDao (
    @SerializedName("lat") var lat : Double? = null,
    @SerializedName("lon") var lon : Double? = null
) : RealmObject()