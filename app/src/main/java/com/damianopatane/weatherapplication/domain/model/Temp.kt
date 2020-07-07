package com.damianopatane.weatherapplication.domain.model

import java.io.Serializable

data class Temp (
	var day : Double?,
	var min : Double?,
	var max : Double?,
	var night : Double?,
	var eve : Double?,
	var morn : Double?
) : Serializable