package com.technorapper.root.di.module

import com.technorapper.root.data.RootApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://us-central1-healthscore-4fcdf.cloudfunctions.net/"

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): RootApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            /* .addConverterFactory(MoshiConverterFactory.create())*/
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create(RootApi::class.java)

    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOKHttpClient(
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