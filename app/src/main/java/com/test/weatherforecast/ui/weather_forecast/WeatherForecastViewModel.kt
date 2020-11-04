package com.test.weatherforecast.ui.weather_forecast

import android.app.Application
import com.test.weatherforecast.WeatherForecastApplication
import com.test.weatherforecast.architecture.BaseViewModel

class WeatherForecastViewModel(application: Application) : BaseViewModel<WeatherForecastRepository>(application) {

    override fun inject() = WeatherForecastApplication.appComponent.inject(this)
}