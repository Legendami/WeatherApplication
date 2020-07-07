package com.damianopatane.weatherapplication.data.source.local

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.damianopatane.weatherapplication.app.WeatherApplication.Companion.getRealm
import com.damianopatane.weatherapplication.data.source.local.dao.CityDao
import com.damianopatane.weatherapplication.data.source.local.dao.CityWeatherDao
import com.damianopatane.weatherapplication.utils.Util
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import io.realm.Realm
import io.realm.kotlin.where
import java.lang.reflect.Type

class DatabaseRealm(private val gson : Gson,
                    private val context : Context) {
    var realm : Realm = getRealm()
    private val handler: Handler = Handler(Looper.getMainLooper())

    fun getWeatherEntries() : List<CityDao>
    {
        return try {
            realm = Realm.getDefaultInstance()
            realm.where<CityDao>().findAll().toList<CityDao>()
        } catch (t : Throwable ) {
            onError(t)
            emptyList()
        }
    }

    fun saveWeatherEntries(entries: List<CityDao>)
    {
        setupRealm()
        try {
            entries.forEach {city ->
                realm.executeTransaction {
                    val type: Type = object : TypeToken<CityDao>() {}.type
                    val json = gson.toJson(city, type)
                    realm.createOrUpdateObjectFromJson(CityDao::class.java, json)
                }
            }
        } catch (t: Throwable) {
            onError(t)
        }
    }

    fun getCityWeatherDaoJson(cityWeatherDao : CityWeatherDao) : String
    {
        realm = Realm.getDefaultInstance()
        return gson.toJson(realm.copyFromRealm(cityWeatherDao))
    }

    private fun onError(t: Throwable) {
        handler.post {
            Util.showMessage(
                context,
                t.localizedMessage
            )
        }
    }

    private fun setupRealm()
    {
        realm = Realm.getDefaultInstance()

        realm.executeTransaction { realm ->
            realm.deleteAll()
        }
    }

    fun checkDataAvailable() : Boolean {
        return getRealm().where<CityDao>().findFirst() != null
    }
}