package com.technorapper.root.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.technorapper.root.data.repository.MainActivityRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseClass : AppCompatActivity() {
    @Inject
    lateinit var repository: MainActivityRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        attachViewModel()

    }

    protected abstract fun setBinding()

    protected abstract fun attachViewModel()

}