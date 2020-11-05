package com.test.weatherforecast.di.components

import com.test.weatherforecast.api.sevices.WeatherForecastApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideWeatherForecastApi(retrofit: Retrofit): WeatherForecastApiService =
        retrofit.create(WeatherForecastApiService::class.java)
}