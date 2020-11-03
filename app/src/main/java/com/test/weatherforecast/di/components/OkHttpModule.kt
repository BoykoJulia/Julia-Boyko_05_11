package com.test.weatherforecast.di.components

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

private const val TIMEOUT = 20000
private const val TIMEOUT_READ = 60000
private const val TIMEOUT_WRITE = 60000

@Module
class OkHttpModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .writeTimeout(TIMEOUT_WRITE.toLong(), TimeUnit.MILLISECONDS)
            .readTimeout(TIMEOUT_READ.toLong(), TimeUnit.MILLISECONDS)
            .build()
    }
}