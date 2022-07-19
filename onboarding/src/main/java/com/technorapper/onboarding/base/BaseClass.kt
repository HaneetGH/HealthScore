package com.technorapper.onboarding.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


abstract class BaseClass : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        attachViewModel()

    }
    protected abstract fun setBinding()

    protected abstract fun attachViewModel()

}