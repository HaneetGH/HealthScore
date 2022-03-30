package com.technorapper.healthscore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.technorapper.onboarding.ui.onboarding.LoginRootActivity
import com.technorapper.root.extension.userDataStore
import com.technorapper.root.proto.ProtoUserRepo
import com.technorapper.root.proto.ProtoUserRepoImpl
import com.technorapper.onboarding.ui.onboarding.OnBoardingActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private var userRepo: ProtoUserRepo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this@MainActivity, LoginRootActivity::class.java))
        userRepo = ProtoUserRepoImpl(this.userDataStore)
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