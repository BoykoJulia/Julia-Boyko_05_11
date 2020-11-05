package com.test.weatherforecast.di.components

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test.weatherforecast.api.convertors.WeatherForecastResponseConverter
import com.test.weatherforecast.data.WeatherForecastResponse
import dagger.Module
import dagger.Provides
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class GsonModule {

    @Singleton
    @Provides
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
            .setLenient()
            .registerTypeAdapter(WeatherForecastResponse::class.java, WeatherForecastResponseConverter())
        return gsonBuilder.create()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }
}