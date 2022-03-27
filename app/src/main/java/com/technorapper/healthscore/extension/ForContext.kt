package com.technorapper.healthscore.extension

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.technorapper.healthscore.UserStore
import com.technorapper.healthscore.proto.UserStoreSerializer

object ForContext {
    private const val USER_PREFERENCES_NAME = "user_preferences"
    private const val DATA_STORE_FILE_NAME = "user_store.pb"
    private const val SORT_ORDER_KEY = "sort_order"
    private val Context.userDataStore: DataStore<UserStore> by dataStore(
        fileName = DATA_STORE_FILE_NAME,
        serializer = UserStoreSerializer
    )
}