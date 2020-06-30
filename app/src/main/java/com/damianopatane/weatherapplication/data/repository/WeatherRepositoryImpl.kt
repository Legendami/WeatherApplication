package com.damianopatane.weatherapplication.data.repository

import android.content.res.AssetManager
import android.os.Handler
import android.os.Looper
import com.damianopatane.weatherapplication.app.Constants.JSON_DATA
import com.damianopatane.weatherapplication.app.Constants.NO_DATA
import com.damianopatane.weatherapplication.app.WeatherApplication.Companion.applicationContext
import com.damianopatane.weatherapplication.data.source.local.DatabaseRealm
import com.damianopatane.weatherapplication.data.source.local.dao.CityDao
import com.damianopatane.weatherapplication.data.source.remote.WeatherAPIService
import com.damianopatane.weatherapplication.domain.model.CityItem
import com.damianopatane.weatherapplication.domain.model.CityWeather
import com.damianopatane.weatherapplication.domain.repository.WeatherDataRepository
import com.damianopatane.weatherapplication.helpers.ModelConverter
import com.damianopatane.weatherapplication.helpers.Keys
import com.damianopatane.weatherapplication.utils.Util.showMessage
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.io.IOException
import java.lang.reflect.Type

class WeatherRepositoryImpl(private val database: DatabaseRealm,
                            private val weatherApiService: WeatherAPIService,
                            private val isNetworkAvailable: Boolean,
                            private val modelConverter : ModelConverter,
                            private val gson : Gson) : WeatherDataRepository {

    private val handler: Handler = Handler(Looper.getMainLooper())
    private var localDataAvailable = database.checkDataAvailable()
    var cities : ArrayList<CityItem> = arrayListOf()
    var citiesDao : List<CityDao> = arrayListOf()

    val getAPIKey: String = Keys.apiKey(1)

    private fun receiveCityList() : ArrayList<CityItem> {
        val cityType: Type = object : TypeToken<List<CityItem?>?>() {}.getType()
        return gson.fromJson(getDataFromAssets(), cityType)
    }

    fun saveCityList() {
        citiesDao = modelConverter.ConvertToCityDao(cities)
        database.saveWeatherEntries(citiesDao)
    }

    fun saveFullCityItem(city : CityItem) {
        val CityDao = modelConverter.ConvertToCityDao(listOf(city))
        database.saveWeatherEntries(CityDao)
    }

    override suspend fun getCurrentCityEntries() : List<CityItem> {
        cities = receiveCityList()

        if (isNetworkAvailable) {
            cities.forEach {cityItem ->
                cityItem.cityWeather = getCurrentWeatherFromServer(cityItem)
            }
            saveCityList()
        }
        else if (localDataAvailable) {
            citiesDao =  database.getWeatherEntries()
            cities =  ArrayList(modelConverter.ConvertToCityItem(citiesDao))
        }
        if (cities.isNullOrEmpty()) {
            handler.post {showMessage(applicationContext(), NO_DATA)}
        }
        return cities
    }

    override suspend fun getFullCityEntry(item : CityItem) : CityItem {
        item.cityWeather = getFullWeatherFromServer(item)
        saveFullCityItem(item)
        return item
    }

    suspend fun getCurrentWeatherFromServer(cityItem : CityItem): CityWeather {
        return weatherApiService.getCurrentReport(cityItem.coord.lat, cityItem.coord.lon, getAPIKey).body() as CityWeather
    }

    suspend fun getFullWeatherFromServer(cityItem : CityItem): CityWeather {
        return weatherApiService.getFullReport(cityItem.coord.lat, cityItem.coord.lon, getAPIKey).body() as CityWeather
    }

    override fun refreshLocalData(entries: List<CityDao>) {
        TODO("Not yet implemented")
    }

    override fun onError(t: Throwable) {
        handler.post {showMessage(applicationContext(), t.localizedMessage)}
    }

    private fun getDataFromAssets(): String? {
        var jsonString: String
        try {
            val assetManager: AssetManager = applicationContext().assets
            val inputStream = assetManager.open(JSON_DATA)
            jsonString = inputStream.bufferedReader().use{it.readText()}
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}