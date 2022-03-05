package com.technorapper.onboarding.di.module

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun picasso(@ApplicationContext app: Context): Picasso? {


        /* // client.setRetryOnConnectionFailure(true);
        // Create A Retry Policy
        client.interceptors().add((Interceptor) chain -> {
            Request request = chain.request();
            // try the request
            Response response = chain.proceed(request);
            int tryCount = 0;
            while (!response.isSuccessful() && tryCount < MAX_RETRY_TIME) {
                Log.d("Picasso","intercept :" + " Request is not successful - " + tryCount);
                tryCount++;
                // retry the request
                response = chain.proceed(request);
            }
            // otherwise just pass the original response on
            return response;
        });*/
        val cache = Cache(File(app.getFilesDir(), "whidephotos"), Long.MAX_VALUE)
        val okHttp3Client = OkHttpClient()
        val MaxCacheDays = 7
        provideCacheInterceptor(MaxCacheDays)?.let {
            okHttp3Client.newBuilder().cache(cache)
                .addInterceptor(it)
        }
        return Picasso.Builder(app.getApplicationContext())
            .loggingEnabled(true)
            .downloader(OkHttp3Downloader(okHttp3Client))
            .build()
    }

    fun provideCacheInterceptor(maxDays: Int): Interceptor? {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())
            val cacheControl: CacheControl = CacheControl.Builder()
                .maxAge(maxDays, TimeUnit.DAYS)
                .build()
            response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()
        }
    }
}