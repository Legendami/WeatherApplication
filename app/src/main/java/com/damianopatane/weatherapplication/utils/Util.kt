package com.damianopatane.weatherapplication.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.damianopatane.weatherapplication.domain.model.ErrorResponse
import com.google.gson.GsonBuilder

import java.io.IOException
import okhttp3.ResponseBody

object Util {
    fun showErrorMessage(context: Context, errorBody: ResponseBody) {

        val gson = GsonBuilder().create()
        val errorResponse: ErrorResponse
        try {
            errorResponse =
                gson.fromJson<ErrorResponse>(errorBody.string(), ErrorResponse::class.java)
            showMessage(
                context,
                errorResponse.message
            )
        } catch (e: IOException) {
            Log.e("Exception ", e.toString())
        }
    }

    fun showMessage(context: Context, msg: String?) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}