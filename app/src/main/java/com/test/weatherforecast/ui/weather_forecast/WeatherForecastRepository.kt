package com.test.weatherforecast.ui.weather_forecast

import com.google.android.gms.maps.model.LatLng
import com.test.weatherforecast.api.sevices.WeatherForecastApiService
import com.test.weatherforecast.architecture.BaseRepository
import com.test.weatherforecast.data.WeatherForecastResponse
import io.reactivex.Single
import javax.inject.Inject

private const val DAYS_COUNT = 3 // I can get weather forecast only for 3 days by using free account

class WeatherForecastRepository @Inject constructor(private val weatherForecastApiService: WeatherForecastApiService) :
    BaseRepository() {

    fun loadWeatherForecast(selectedLatLng: LatLng?) : Single<WeatherForecastResponse> {
        val coordinate = "${selectedLatLng?.latitude},${selectedLatLng?.longitude}"

        return weatherForecastApiService.getWeatherForecast(coordinate, DAYS_COUNT)
    }
}