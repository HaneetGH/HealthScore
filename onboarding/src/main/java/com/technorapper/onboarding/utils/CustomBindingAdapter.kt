package com.technorapper.onboarding.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.DecimalFormat


object CustomBindingAdapter {


    @JvmStatic
    @BindingAdapter("android:date")
    fun date(view: TextView, timestamp: Long) {
        view.text = Util.getDate(timestamp)
    }

    @JvmStatic
    @BindingAdapter("android:dayName")
    fun dayName(view: TextView, timestamp: Long) {
        view.text = Util.getDayName(timestamp)
    }

    @JvmStatic
    @BindingAdapter("android:hour")
    fun hour(view: TextView, timestamp: Int) {
        view.text = Util.getHour(timestamp.toLong())
    }


    @JvmStatic
    @BindingAdapter("android:visibility")
    fun visibility(view: TextView, meter: Double) {
       // view.setText("${meter/1000} KM")
        view.text = "${DecimalFormat("##.##").format((meter/1000).toDouble())} KM"
    }
    @JvmStatic
    @BindingAdapter("android:temp")
    fun temp(view: TextView, temp: Double) {
        // view.setText("${meter/1000} KM")
        view.text = "${DecimalFormat("##").format((temp).toDouble())}\u00B0"
    }

    @JvmStatic
    @BindingAdapter("android:wind")
    fun wind(view: TextView, mps: Double) {
        val kmps= mps * 3.6

        view.text = "${DecimalFormat("##.##").format((kmps).toDouble())}km/h"
    }

}