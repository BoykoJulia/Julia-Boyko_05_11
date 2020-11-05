package com.test.weatherforecast.api.sevices

import com.test.weatherforecast.data.WeatherForecastResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastApiService {

    @GET("v1/forecast.json")
    fun getWeatherForecast(
        @Query("q") selectedPlace: String?,
        @Query("days") days: Int
    ): Single<WeatherForecastResponse>
}