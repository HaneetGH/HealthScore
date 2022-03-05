package com.technorapper.root.data.data_model.weather

import com.google.gson.annotations.SerializedName

data class MinutelyItem(

    @field:SerializedName("dt")
    val dt: Int = 0,

    @field:SerializedName("precipitation")
    val precipitation: Double = 0.0
)