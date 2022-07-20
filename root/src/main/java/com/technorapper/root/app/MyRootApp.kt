package com.technorapper.root.app


import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
open class MyRootApp : Application() {

    override fun onCreate() {
        super.onCreate()

    }

    fun solution(X: IntArray, S: Int): Int {


        val prefixes = HashMap<Int, Int?>()
        var result = 0
        val P = IntArray(X.size + 1)
        prefixes[0] = 1

        val Q = IntArray(X.size + 1)
        P[0] = 0
        Q[0] = 0

        for (i in 1 until X.size + 1) {
            P[i] = (P[i - 1] + X.get(i - 1)).toInt()
            Q[i] = P[i] - S * i
            if (!prefixes.containsKey(Q[i])) {
                prefixes[Q[i]] = 1
            } else {
                var temp = prefixes[Q[i]]
                temp = temp!! +1
                prefixes[Q[i]] = temp
            }
        }

        for ((_, value) in prefixes) {
            result += value!! * (value - 1) / 2
        }

        return result
    }
}