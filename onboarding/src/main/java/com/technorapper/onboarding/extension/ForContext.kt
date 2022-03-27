package com.technorapper.onboarding.extension

import android.app.Activity
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.technorapper.onboarding.UserStore
import com.technorapper.onboarding.proto.UserStoreSerializer

private const val USER_PREFERENCES_NAME = "user_preferences"
private const val DATA_STORE_FILE_NAME = "user_store.pb"
private const val SORT_ORDER_KEY = "sort_order"
val Activity.userDataStore: DataStore<UserStore> by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = UserStoreSerializer
)