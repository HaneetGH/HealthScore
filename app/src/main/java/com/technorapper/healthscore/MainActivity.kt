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
import com.technorapper.root.ui.list.RootActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private var userRepo: ProtoUserRepo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userRepo = ProtoUserRepoImpl(this.userDataStore)
        CheckFlow()
    }

    private fun CheckFlow() {
        lifecycleScope.launch {
            userRepo?.getTokenID()?.collect { state ->
                if (state.isEmpty()) {
                    startActivity(Intent(this@MainActivity, OnBoardingActivity::class.java))
                    finishAffinity()
                } else {
                    startActivity(
                        Intent(
                            this@MainActivity,
                            RootActivity::class.java
                        )
                    )
                    finishAffinity()
                }
            }
        }
    }
}
