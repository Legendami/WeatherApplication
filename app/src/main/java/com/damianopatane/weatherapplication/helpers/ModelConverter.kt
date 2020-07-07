package com.damianopatane.weatherapplication.helpers

import com.damianopatane.weatherapplication.data.source.local.DatabaseRealm
import com.damianopatane.weatherapplication.data.source.local.dao.*
import com.damianopatane.weatherapplication.domain.model.*
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.lang.reflect.Type


class ModelConverter(private val gson : Gson,
                     private val database : DatabaseRealm) {

    fun convertToCityItem(cityItems : List<CityDao>?) : List<CityItem> {
        val items : ArrayList<CityItem> = ArrayList()
        val entries : List<CityDao>? = cityItems?.distinctBy { it.name }
        entries?.forEach{
            val newItem = CityItem(
                it.id,
                it.name,
                it.state,
                it.country,
                CoordVal(it.coordDao?.lon, it.coordDao?.lat),
                it.cityWeatherDao?.convertToCityWeather()
            )
            items.add(newItem)
        }
        return items
    }

    fun convertToCityDao(cityItems : List<CityItem>?) : List<CityDao> {
        val entries : ArrayList<CityDao> = ArrayList()
        val items : List<CityItem>? = cityItems?.distinctBy { it.name }
        items?.forEach{
            val newItem = CityDao(
                CoordDao(it.coord?.lon, it.coord?.lat),
                it.country,
                it.cityWeather?.convertToCityWeatherDao(),
                it.id,
                it.name,
                it.state)
            entries.add(newItem)
        }
        return entries
    }

    private fun CityWeatherDao.convertToCityWeather() : CityWeather {
        val type : Type = object : TypeToken<CityWeather>() {}.type
        val json = database.getCityWeatherDaoJson(this)
        return gson.fromJson(json, type)
    }

    private fun CityWeather.convertToCityWeatherDao() : CityWeatherDao {
        val daoType : Type = object : TypeToken<CityWeatherDao>() {}.type
        val type : Type = object : TypeToken<CityWeather>() {}.type
        val json = gson.toJson(this, type)
        return gson.fromJson(json, daoType)
    }
}
