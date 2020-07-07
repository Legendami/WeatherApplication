package com.damianopatane.weatherapplication.domain.model

import java.io.Serializable

data class Weather (
	var id : Int?,
	var main : String?,
	var description : String?,
	var icon : String?
) : Serializable