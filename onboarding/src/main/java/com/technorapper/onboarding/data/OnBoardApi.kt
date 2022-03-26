package com.technorapper.onboarding.data


import com.technorapper.onboarding.data.data_model.BasicResult
import com.technorapper.onboarding.data.data_model.weather.WeatherForecastResponse
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OnBoardApi {

    @POST("addNpUsers")
    suspend fun addusers(
        @Query("useremail") useremail: String,
        @Query("profession") profession: String,
        @Query("userDOB") userDOB: String,
        @Query("userID") userID: String,
        @Query("userLocation") userLocation: String,
        @Query("userName") userName: String,
        @Query("idToken") idToken: String,
        @Query("uid") uid: String
    ): BasicResult

    @POST("isProfileThere")
    suspend fun isProfileThere(
        @Query("uid") userID: String,
        @Query("idToken") idToken: String
    ): BasicResult
}