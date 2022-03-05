package com.technorapper.root.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*


object Util {
    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun getDate(timestamp: Long): String {
        try {
            val c = Calendar.getInstance()
            //SimpleDateFormat sdf = new SimpleDateFormat("dd MMM YYYY hh:mm", Locale.ENGLISH);
            val sdf = SimpleDateFormat("MM/dd", Locale.ENGLISH)
            val tempdate: String
            val smsTime = Calendar.getInstance()
            smsTime.timeInMillis = timestamp * 1000L
            tempdate =/* if (c[Calendar.YEAR] == smsTime[Calendar.YEAR]) {
                if (c[Calendar.DAY_OF_YEAR] == smsTime[Calendar.DAY_OF_YEAR]) {
                    "Today"
                } *//*else if (c[Calendar.DATE] - smsTime[Calendar.DATE] == 1) {
                context.getString(R.string.yesterday)
            }*//* else if (c[Calendar.DAY_OF_YEAR] - smsTime[Calendar.DAY_OF_YEAR] == -1) {
                    "Tomorrow"
                } else {
                    sdf.format(Date(timestamp * 1000L))
                }
            } else {
                sdf.format(Date(timestamp * 1000L))
            }*/

                sdf.format(Date(timestamp * 1000L))
            Log.d("dateeeeee", sdf.format(Date(timestamp * 1000L)) + "\n")
            return tempdate
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getDayName(timestamp: Long): String {
        try {
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("EEE", Locale.ENGLISH)
            val tempdate: String
            val smsTime = Calendar.getInstance()
            smsTime.timeInMillis = timestamp * 1000L
            tempdate = sdf.format(Date(timestamp * 1000L))
            return tempdate
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getHour(timestamp: Long): String {
        try {
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("HH:mm", Locale.ENGLISH)
            val tempdate: String
            val smsTime = Calendar.getInstance()
            smsTime.timeInMillis = timestamp * 1000L
            tempdate = sdf.format(Date(timestamp * 1000L))
            return tempdate
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }


}