package com.technorapper.onboarding.ui.onboarding

import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import com.technorapper.onboarding.R
import com.technorapper.onboarding.base.BaseClass
import com.technorapper.onboarding.databinding.OnboardActivityBinding


class OnBoardingActivity : BaseClass() {


    private val viewModel by viewModels<OnBoardingViewModel>()
    lateinit var binding: OnboardActivityBinding


    override fun setBinding() {
        binding =
            DataBindingUtil.setContentView(this, R.layout.onboard_activity)
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


