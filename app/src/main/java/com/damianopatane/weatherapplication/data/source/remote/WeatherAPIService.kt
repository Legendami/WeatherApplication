package com.damianopatane.weatherapplication.data.source.remote

import com.damianopatane.weatherapplication.domain.model.CityWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPIService {

    @GET("/data/2.5/onecall?exclude=minutely,hourly,daily&units=metric")
    suspend fun getCurrentReport(@Query("lat") lat : Double,
                                 @Query("lon") lon : Double,
                                 @Query("appid") token : String): Response<CityWeather>
    @GET("/data/2.5/onecall?exclude=minutely&units=metric")
    suspend fun getFullReport(@Query("lat") lat : Double,
                              @Query("lon") lon : Double,
                              @Query("appid") token : String): Response<CityWeather>
}