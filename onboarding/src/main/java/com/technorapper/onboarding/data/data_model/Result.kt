package com.technorapper.onboarding.data.data_model

data class Result(
    val `data`: Data,
    val message: String,
    val status_code: Int,
    val status_message: String
)