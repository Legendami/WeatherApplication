package com.damianopatane.weatherapplication.domain.model

import java.io.Serializable

data class Hourly (
	var dt : Int,
	var temp : Double,
	var feels_like : Double,
	var pressure : Int,
	var humidity : Int,
	var dew_point : Double,
	var clouds : Int,
	var wind_speed : Double,
	var wind_deg : Int,
	var weather : List<Weather>
) : Serializable