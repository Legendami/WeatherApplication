package com.damianopatane.weatherapplication.domain.model

import io.realm.RealmObject

open class ErrorResponse(
    var message: String? = null
) : RealmObject()