package com.test.weatherforecast.di.components

import com.test.weatherforecast.BuildConfig
import com.test.weatherforecast.api.interceptors.WeatherAuthInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val TIMEOUT = 20000
private const val TIMEOUT_READ = 60000
private const val TIMEOUT_WRITE = 60000

@Module
class OkHttpModule {

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        weatherAuthInterceptor: WeatherAuthInterceptor
    ): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(weatherAuthInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .writeTimeout(TIMEOUT_WRITE.toLong(), TimeUnit.MILLISECONDS)
            .readTimeout(TIMEOUT_READ.toLong(), TimeUnit.MILLISECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    @Singleton
    @Provides
    fun provideWeatherForecastInterceptor() = WeatherAuthInterceptor()
}