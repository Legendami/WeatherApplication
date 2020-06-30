package com.damianopatane.weatherapplication.helpers

import com.damianopatane.weatherapplication.data.source.local.DatabaseRealm
import com.damianopatane.weatherapplication.data.source.local.dao.*
import com.damianopatane.weatherapplication.domain.model.*
import com.google.common.reflect.TypeToken
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.realm.RealmObject
import java.lang.reflect.Type


class ModelConverter(private val gson : Gson,
                     private val database : DatabaseRealm) {

    fun ConvertToCityItem(cityItems : List<CityDao>?) : List<CityItem> {
        var entries : ArrayList<CityItem> = ArrayList()
        var items : List<CityDao>? = cityItems!!.distinctBy { it.name }
        items!!.forEach{
            val newItem = CityItem(
                it.id!!,
                it.name!!,
                it.state!!,
                it.country!!,
                CoordVal(it.coordDao!!.lon!!, it.coordDao!!.lat!!),
                ConvertToCityWeather(it.cityWeatherDao!!)
            )
            entries.add(newItem)
        }
        return entries
    }

    fun ConvertToCityDao(cityItems : List<CityItem>?) : List<CityDao> {
        var items : ArrayList<CityDao> = ArrayList()
        var entries : List<CityItem>? = cityItems!!.distinctBy { it.name }
        cityItems!!.forEach{
            val newItem = CityDao(
                CoordDao(it.coord.lon, it.coord.lat),
                it.country,
                ConvertToCityWeatherDao(it.cityWeather),
                it.id,
                it.name,
                it.state)
            items.add(newItem)
        }
        return items
    }

    private fun ConvertToCityWeather(cityWeatherDao : CityWeatherDao) : CityWeather {
        val Type : Type = object : TypeToken<CityWeather>() {}.getType()
        val json = database.getCityWeatherDaoJson(cityWeatherDao)
        val cityWeather : CityWeather = gson.fromJson(json, Type)
        return cityWeather
    }

    private fun ConvertToCityWeatherDao(cityWeather : CityWeather) : CityWeatherDao {
        val daoType : Type = object : TypeToken<CityWeatherDao>() {}.getType()
        val Type : Type = object : TypeToken<CityWeather>() {}.getType()
        val json = gson.toJson(cityWeather, Type)
        val cityWeatherDao : CityWeatherDao = gson.fromJson(json, daoType)
        return cityWeatherDao
    }
}
