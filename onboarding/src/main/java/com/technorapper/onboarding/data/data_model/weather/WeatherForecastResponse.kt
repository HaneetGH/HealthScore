package com.technorapper.onboarding.data.data_model.weather

import com.google.gson.annotations.SerializedName
import com.technorapper.onboarding.data.data_model.weather.Current

data class WeatherForecastResponse(

    @field:SerializedName("current")
    val current: Current = Current(),

    @field:SerializedName("timezone")
    val timezone: String = "",

    @field:SerializedName("timezone_offset")
    val timezoneOffset: Int = 0,

    @field:SerializedName("daily")
    val daily: ArrayList<DailyItem> = ArrayList<DailyItem>(),

    @field:SerializedName("lon")
    val lon: Double = 0.0,

    @field:SerializedName("hourly")
    val hourly: ArrayList<HourlyItem> = ArrayList<HourlyItem>(),

    @field:SerializedName("minutely")
    val minutely: ArrayList<MinutelyItem> = ArrayList<MinutelyItem>(),

    @field:SerializedName("lat")
    val lat: Double = 0.0
)


