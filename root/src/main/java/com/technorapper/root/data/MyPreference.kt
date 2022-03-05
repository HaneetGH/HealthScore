package com.technorapper.root.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyPreference @Inject constructor(@ApplicationContext context: Context) {
    var PREF_TAG = "unit"
    val prefs = context.getSharedPreferences(
        "my_preferences", Context.MODE_PRIVATE
    );

    fun getStoredUnit(): String {
        return prefs.getString(PREF_TAG, "metric")!!
    }

    fun setStoredUnit(query: String) {
        prefs.edit().putString(PREF_TAG, query).apply()
    }
}