package com.damianopatane.weatherapplication.security

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64

object SharedPrefUtils {
    private const val PREF_NAME = "realm_key"
    private const val KEY = "iv_and_encrypted_key"
    fun save(context: Context, ivAndEncryptedKey: ByteArray?) {
        getPreference(context).edit()
            .putString(KEY, encode(ivAndEncryptedKey))
            .apply()
    }

    fun load(context: Context): ByteArray? {
        val pref = getPreference(context)
        val ivAndEncryptedKey = pref.getString(KEY, null) ?: return null
        return decode(ivAndEncryptedKey)
    }

    private fun encode(data: ByteArray?): String? {
        return if (data == null) {
            null
        } else Base64.encodeToString(data, Base64.NO_WRAP)
    }

    private fun decode(encodedData: String?): ByteArray? {
        return if (encodedData == null) {
            null
        } else Base64.decode(encodedData, Base64.DEFAULT)
    }

    private fun getPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}