package com.technorapper.onboarding.ui.onboarding

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.technorapper.onboarding.base.BaseClass

class LoginRootActivity : BaseClass() {
    private val viewModel by viewModels<OnBoardingViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(viewModel)
        }
    }

    override fun setBinding() {

    }

    override fun attachViewModel() {

    }
}
