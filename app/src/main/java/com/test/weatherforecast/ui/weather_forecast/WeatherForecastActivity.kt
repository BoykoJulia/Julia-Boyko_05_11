package com.test.weatherforecast.ui.weather_forecast

import android.content.Context
import android.content.Intent
import com.google.android.gms.maps.model.LatLng
import com.test.weatherforecast.R
import com.test.weatherforecast.architecture.binding.BaseBindingActivity
import com.test.weatherforecast.databinding.ActivityWeatherForecastBinding

private const val EXTRA_LAT_LNG = "lat_Lng"

class WeatherForecastActivity :
    BaseBindingActivity<WeatherForecastViewModel, ActivityWeatherForecastBinding>() {

    companion object {
        fun newIntent(context: Context, latLng: LatLng): Intent {
            val intent = Intent(context, WeatherForecastActivity::class.java)
            intent.putExtra(EXTRA_LAT_LNG, latLng)

            return intent
        }
    }

    override val layoutId = R.layout.activity_weather_forecast

    override fun setDataToBinding() {
        binding.viewModel = viewModel
    }

    override fun createViewModel(): WeatherForecastViewModel {
        return provideViewModel { WeatherForecastViewModel(it) }
    }
}