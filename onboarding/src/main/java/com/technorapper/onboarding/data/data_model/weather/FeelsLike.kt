package com.technorapper.onboarding.data.data_model.weather

import com.google.gson.annotations.SerializedName

data class FeelsLike(

    @field:SerializedName("eve")
    val eve: Double = 0.0,

    @field:SerializedName("night")
    val night: Double = 0.0,

    @field:SerializedName("day")
    val day: Double = 0.0,

    @field:SerializedName("morn")
    val morn: Double = 0.0
)










