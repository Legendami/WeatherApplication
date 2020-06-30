package com.damianopatane.weatherapplication.domain.repository

import com.damianopatane.weatherapplication.data.source.local.dao.CityDao
import com.damianopatane.weatherapplication.domain.model.CityItem

interface WeatherDataRepository {
    suspend fun getCurrentCityEntries() : List<CityItem>
    fun onError(t: Throwable)
    fun refreshLocalData(entries: List<CityDao>)
    suspend fun getFullCityEntry(item : CityItem) : CityItem
}