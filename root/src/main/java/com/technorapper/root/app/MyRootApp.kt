package com.technorapper.root.app



import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class MyRootApp : Application() {

    override fun onCreate() {
        super.onCreate()

    }

}