package com.damianopatane.weatherapplication.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Feels_like (
	var day : Double,
	var night : Double,
	var eve : Double,
	var morn : Double
) : Serializable