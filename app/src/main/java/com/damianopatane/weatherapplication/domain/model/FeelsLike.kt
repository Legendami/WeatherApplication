package com.damianopatane.weatherapplication.domain.model

import java.io.Serializable

data class FeelsLike (
	var day : Double?,
	var night : Double?,
	var eve : Double?,
	var morn : Double?
) : Serializable