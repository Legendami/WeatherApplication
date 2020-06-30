package com.damianopatane.weatherapplication.data.source.remote

import android.os.Handler
import android.os.Looper

class RemoteDataSource {

    private val weatherAPIService: WeatherAPIService =
        RetrofitClient.weatherAPIService
    private val handler: Handler = Handler(Looper.getMainLooper())

//    override suspend fun getWeatherEntries(): List<WeatherEntry> {
//        try {
//            return weatherAPIService.getWeatherReports().body()!!.toList()
//        }
//        catch (t: Throwable)
//        {
//            onError(t)
//            return emptyList()
//        }
//    }
//
//
//    override fun onError(t: Throwable) {
//        handler.post{ showMessage(applicationContext(), t.localizedMessage) }
//    }

    companion object {
        private var INSTANCE: RemoteDataSource? = null
        fun getInstance(): RemoteDataSource {
            if (INSTANCE == null) {
                INSTANCE =
                    RemoteDataSource()
            }
            return INSTANCE!!
        }
    }
}