package com.test.weatherforecast.di.components

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule constructor(private val application: Application) {

    @Singleton
    @Provides
    fun provideAppContext(): Context = application
}