package com.technorapper.healthscore.application



import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class YoApp : Application() {

    override fun onCreate() {
        super.onCreate()

    }

}