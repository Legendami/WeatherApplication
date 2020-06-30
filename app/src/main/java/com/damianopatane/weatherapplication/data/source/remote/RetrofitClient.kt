package com.damianopatane.weatherapplication.data.source.remote

import com.damianopatane.weatherapplication.app.Constants
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


object RetrofitClient {

    var spec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).tlsVersions(TlsVersion.TLS_1_2)
            .cipherSuites(CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384)
            .build()

    var okHttpClient = OkHttpClient.Builder()
        .connectionSpecs(Collections.singletonList(spec))
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val weatherAPIService: WeatherAPIService = retrofit.create(
        WeatherAPIService::class.java)
}