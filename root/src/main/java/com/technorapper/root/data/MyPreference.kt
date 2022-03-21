package com.technorapper.root.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyPreference @Inject constructor(@ApplicationContext context: Context) {
    var PREF_UID = "UID"
    var PREF_FIREBASE_TOKEN_ID="firebaseTokenID"
    val prefs = context.getSharedPreferences(
        "my_preferences", Context.MODE_PRIVATE
    );

    fun getStoredUnit(): String {
        return prefs.getString(PREF_UID, "uid")!!
    }

    fun setStoredUnit(query: String) {
        prefs.edit().putString(PREF_UID, query).apply()
    }


    fun getStoredfbToken(): String {
        return prefs.getString(PREF_FIREBASE_TOKEN_ID, "firebaseTokenID")!!
    }

    fun setStoredfbToken(query: String) {
        prefs.edit().putString(PREF_FIREBASE_TOKEN_ID, query).apply()
    }
}