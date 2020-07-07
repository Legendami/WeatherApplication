
package com.damianopatane.weatherapplication.domain.model

import java.io.Serializable

data class Daily (
	var dt : Int?,
	var sunrise : Int?,
	var sunset : Int?,
	var temp : Temp?,
	var feels_like : FeelsLike?,
	var pressure : Int?,
	var humidity : Int?,
	var dew_point : Double?,
	var wind_speed : Double?,
	var wind_deg : Int?,
	var weather : List<Weather?>,
	var clouds : Int?,
	var uvi : Double?
) : Serializable