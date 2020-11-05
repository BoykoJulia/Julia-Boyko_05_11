package com.test.weatherforecast.data

data class WeatherForecastResponse(
    var locationName: String? = null,
    var forecasts: ArrayList<Forecast>? = null
)