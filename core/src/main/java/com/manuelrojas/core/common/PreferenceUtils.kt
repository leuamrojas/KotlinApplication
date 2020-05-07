package com.manuelrojas.core.common

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import javax.inject.Inject

private const val KEY_LOCATION = "keyLocation"

class PreferenceUtils @Inject constructor(context: Context){

    private val appContext = context.applicationContext

    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun saveLastLocation(location: String?) {
        preference.edit().putString(KEY_LOCATION, location).apply()
    }

    fun getLastLocation() : String? {
        return preference.getString(KEY_LOCATION, null)
    }

}