package com.technorapper.onboarding.ui.onboarding

import android.content.Context
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController

import com.technorapper.onboarding.R
import com.technorapper.onboarding.UserStore
import com.technorapper.onboarding.base.BaseClass
import com.technorapper.onboarding.databinding.OnboardActivityBinding
import com.technorapper.onboarding.extension.userDataStore

import com.technorapper.onboarding.proto.ProtoUserRepo
import com.technorapper.onboarding.proto.ProtoUserRepoImpl
import com.technorapper.onboarding.proto.UserStoreSerializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class OnBoardingActivity : BaseClass() {


    private val viewModel by viewModels<OnBoardingViewModel>()
    lateinit var binding: OnboardActivityBinding

    private var userRepo: ProtoUserRepo? = null
    private fun setDataToUI() {
        lifecycleScope.launch {
            userRepo?.getUserID()?.collect { state ->
                withContext(Dispatchers.Main) {
                    Log.d("Value OnBoard", state)
                }
            }
        }
    }

    override fun setBinding() {
        binding =
            DataBindingUtil.setContentView(this, R.layout.onboard_activity)
        userRepo = ProtoUserRepoImpl(this.userDataStore)
        setDataToUI()
        //binding.bottomNavigation.setupWithNavController(Navigation.findNavController(this, R.id.fragmentContainerView))

    }

    override fun onBackPressed() {
        super.onBackPressed()

    }

    override fun attachViewModel() {
        viewModel.pushContext(this@OnBoardingActivity)
        //viewModel.setStateEvent(MainListStateEvent.FetchBookmark)
        //    viewModel.uiState.observe(this, Observer { parse(it) })
    }


}


