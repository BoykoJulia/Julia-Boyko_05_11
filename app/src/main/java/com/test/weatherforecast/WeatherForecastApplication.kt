package com.test.weatherforecast

import android.app.Application
import com.test.weatherforecast.di.AppComponent
import com.test.weatherforecast.di.DaggerAppComponent
import com.test.weatherforecast.di.components.AppModule

class WeatherForecastApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}