package com.test.weatherforecast.api.convertors

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.test.weatherforecast.data.Forecast
import com.test.weatherforecast.data.WeatherForecastResponse
import com.test.weatherforecast.utils.ParseUtils
import java.lang.reflect.Type

class WeatherForecastResponseConverter : JsonDeserializer<WeatherForecastResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): WeatherForecastResponse {
        val parent = json?.asJsonObject

        val weatherForecastResponse = WeatherForecastResponse()
        val forecasts = ArrayList<Forecast>()

        val jsonLocation = ParseUtils.getObjectSafely(parent, "location")

        weatherForecastResponse.locationName = ParseUtils.getStringSafely(jsonLocation, "name")

        val jsonForecast = ParseUtils.getObjectSafely(parent, "forecast")
        val forecastArray = ParseUtils.getArraySafely(jsonForecast, "forecastday")

        forecastArray?.forEach {
            val forecast = Forecast()

            forecast.day = ParseUtils.getStringSafely(it.asJsonObject, "date")

            val jsonDayForecast = ParseUtils.getObjectSafely(it.asJsonObject, "day")
            forecast.maxTemp = ParseUtils.getDoubleSafely(jsonDayForecast, "maxtemp_c")
            forecast.minTemp = ParseUtils.getDoubleSafely(jsonDayForecast, "mintemp_c")

            val jsonCondition = ParseUtils.getObjectSafely(jsonDayForecast, "condition")

            forecast.weatherDescription = ParseUtils.getStringSafely(jsonCondition, "text")
            forecast.icon = ParseUtils.getStringSafely(jsonCondition, "icon")

            forecasts.add(forecast)
        }

        weatherForecastResponse.forecasts = forecasts

        return weatherForecastResponse
    }

}