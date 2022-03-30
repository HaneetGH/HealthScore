package com.technorapper.onboarding.ui.onboarding

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.technorapper.onboarding.base.BaseClass

class LoginRootActivity : BaseClass() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    override fun setBinding() {

    }

    override fun attachViewModel() {

    }
}
