package com.test.weatherforecast.data

data class Forecast(
    var day: String? = null,
    var maxTemp: Double? = null,
    var minTemp: Double? = null,
    var weatherDescription: String? = null,
    var icon: String? = null
)