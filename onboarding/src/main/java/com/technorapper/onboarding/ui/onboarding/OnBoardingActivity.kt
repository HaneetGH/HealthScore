package com.technorapper.onboarding.ui.onboarding

import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController

import com.technorapper.onboarding.R
import com.technorapper.onboarding.base.BaseClass
import com.technorapper.onboarding.databinding.ListActivityBinding

class OnBoardingActivity : BaseClass() {


    private val viewModel by viewModels<OnBoardingViewModel>()
    lateinit var binding: ListActivityBinding


    override fun setBinding() {
        binding =
            DataBindingUtil.setContentView(this, R.layout.list_activity)
        //binding.bottomNavigation.setupWithNavController(Navigation.findNavController(this, R.id.fragmentContainerView))

    }


    override fun attachViewModel() {
        //viewModel.setStateEvent(MainListStateEvent.FetchBookmark)
        //    viewModel.uiState.observe(this, Observer { parse(it) })
    }


}


