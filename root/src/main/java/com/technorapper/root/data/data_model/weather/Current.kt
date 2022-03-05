package com.technorapper.root.data.data_model.weather

import com.google.gson.annotations.SerializedName


data class Current(

    @field:SerializedName("sunrise")
    val sunrise: Double = 0.0,

    @field:SerializedName("temp")
    val temp: Double = 0.0,

    @field:SerializedName("visibility")
    val visibility: Double = 0.0,

    @field:SerializedName("uvi")
    val uvi: Double = 0.0,

    @field:SerializedName("pressure")
    val pressure: Double = 0.0,

    @field:SerializedName("clouds")
    val clouds: Int = 0,

    @field:SerializedName("feels_like")
    val feelsLike: Double = 0.0,

    @field:SerializedName("dt")
    val dt: Int = 0,

    @field:SerializedName("wind_deg")
    val windDeg: Int = 0,

    @field:SerializedName("dew_point")
    val dewPoint: Double = 0.0,

    @field:SerializedName("sunset")
    val sunset: Int = 0,

    @field:SerializedName("weather")
    val weather: ArrayList<WeatherItem> = ArrayList<WeatherItem>(),

    @field:SerializedName("humidity")
    val humidity: Int = 0,

    @field:SerializedName("wind_speed")
    val windSpeed: Double = 0.0
)