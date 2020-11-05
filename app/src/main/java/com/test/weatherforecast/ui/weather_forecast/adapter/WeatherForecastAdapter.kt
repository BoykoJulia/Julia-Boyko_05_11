package com.test.weatherforecast.ui.weather_forecast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.weatherforecast.R
import com.test.weatherforecast.data.Forecast
import com.test.weatherforecast.databinding.ItemForecastBinding
import com.test.weatherforecast.ui.BaseRVAdapter

class WeatherForecastAdapter :
    BaseRVAdapter<Forecast, WeatherForecastAdapter.WeatherForecastViewHolder>() {

    inner class WeatherForecastViewHolder(val binding: ItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForecastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemForecastBinding.inflate(inflater, parent, false)

        return WeatherForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherForecastViewHolder, position: Int) {
        val item = getItem(position) ?: return

        with(holder.binding) {
            tvWeatherDescription.text = item.weatherDescription
            tvDayText.text = if (position == 0) {
               root.context.getString(R.string.item_weather_forecast_today)
            } else item.day

            tvMaxTemperature.text = item.maxTemp?.toString()
            tvMinTemperature.text = item.minTemp?.toString()

            val iconUrl = "http:" + item.icon

            Glide.with(ivWeatherIcon)
                .load(iconUrl)
                .centerInside()
                .into(ivWeatherIcon)
        }

    }

    override fun getItemCount(): Int {
        return getItems()?.size ?: 0
    }
}