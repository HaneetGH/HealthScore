package com.technorapper.onboarding.data

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkLayer {
    companion object {

        var BASE_URL = "https://us-central1-healthscore-4fcdf.cloudfunctions.net/"

        fun create(): OnBoardApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(oKHttpClient(loggingInterceptor()))
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(OnBoardApi::class.java)
        }

        fun loggingInterceptor(): HttpLoggingInterceptor {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return httpLoggingInterceptor
        }

        fun oKHttpClient(
            loggingInterceptor: HttpLoggingInterceptor
        ): OkHttpClient {

            return OkHttpClient.Builder()
                .readTimeout(1200, TimeUnit.SECONDS)
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val requestBuilder: Request.Builder = chain.request().newBuilder()
                    requestBuilder.header("Content-Type", "application/json")
                    requestBuilder.header("x-hasura-admin-secret", "incredible-admin-secret")
                    chain.proceed(requestBuilder.build())
                }
                .connectTimeout(1200, TimeUnit.SECONDS)
                .build()

        }

        private val authInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .build()

            return@Interceptor chain.proceed(request)
        }
    }



}