package com.damianopatane.weatherapplication.helpers

object Keys {

    init {
        System.loadLibrary("native-lib")
    }

    external fun apiKey(id: Int): String
}