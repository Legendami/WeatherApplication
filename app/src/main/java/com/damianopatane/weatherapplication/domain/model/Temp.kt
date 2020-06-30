package com.damianopatane.weatherapplication.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Temp (
	var day : Double,
	var min : Double,
	var max : Double,
	var night : Double,
	var eve : Double,
	var morn : Double
) : Serializable