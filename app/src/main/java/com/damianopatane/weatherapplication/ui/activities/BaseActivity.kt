package com.damianopatane.weatherapplication.ui.activities

import com.damianopatane.weatherapplication.app.WeatherApplication
import dagger.android.support.DaggerAppCompatActivity

open class BaseActivity : DaggerAppCompatActivity() {
    override fun onDestroy() {
        super.onDestroy()
        WeatherApplication.closeRealm()
    }
}