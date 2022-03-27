package com.technorapper.healthscore

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.lifecycleScope
import com.technorapper.healthscore.proto.ProtoUserRepo
import com.technorapper.healthscore.proto.ProtoUserRepoImpl
import com.technorapper.healthscore.proto.UserStoreSerializer
import com.technorapper.onboarding.ui.onboarding.OnBoardingActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val DATA_STORE_FILE_NAME = "user_store.pb"

    val Context.userDataStore: DataStore<UserStore> by dataStore(
        fileName = DATA_STORE_FILE_NAME,
        serializer = UserStoreSerializer
    )
    private var userRepo: ProtoUserRepo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this@MainActivity, OnBoardingActivity::class.java))
        userRepo = ProtoUserRepoImpl(userDataStore)
        lifecycleScope.launch {
            userRepo?.saveUserID("false")
        }
        setDataToUI()
    }

    private fun setDataToUI() {
        lifecycleScope.launch {
            userRepo?.getUserID()?.collect { state ->
                withContext(Dispatchers.Main) {
                    Log.d("Value", state)
                }
            }
        }
    }
}