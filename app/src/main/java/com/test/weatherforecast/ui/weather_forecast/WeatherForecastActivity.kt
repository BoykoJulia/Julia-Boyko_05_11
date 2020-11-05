package com.test.weatherforecast.ui.weather_forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.test.weatherforecast.R
import com.test.weatherforecast.architecture.binding.BaseBindingActivity
import com.test.weatherforecast.databinding.ActivityWeatherForecastBinding
import com.test.weatherforecast.ui.weather_forecast.adapter.WeatherForecastAdapter
import com.test.weatherforecast.utils.extantions.changeVisibility
import com.test.weatherforecast.utils.extantions.setupLightStatusBar

private const val EXTRA_LAT_LNG = "lat_Lng"

class WeatherForecastActivity :
    BaseBindingActivity<WeatherForecastViewModel, ActivityWeatherForecastBinding>() {

    private val weatherForecastAdapter by lazy { WeatherForecastAdapter() }

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
        return provideViewModel {
            WeatherForecastViewModel(
                it,
                selectedLatLng = intent.getParcelableExtra(EXTRA_LAT_LNG)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupLightStatusBar()

        initViews()
        initObservers()
    }

    private fun initViews() {
        initRecyclerView()

        binding.toolbar.ivBack.setOnClickListener {
            viewModel.onBackButtonClicked()
        }
        binding.btnRetry.setOnClickListener {
            viewModel.onButtonRetryClicked()
        }
    }

    private fun initRecyclerView() {
        binding.rvForecasts.adapter = weatherForecastAdapter

        val divider = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider)!!)

        binding.rvForecasts.addItemDecoration(divider)
    }

    private fun initObservers() {
        viewModel.ldLocationTitle.observe(this, {
            binding.toolbar.tvTitle.text = it
        })

        viewModel.ldWeatherForecastItems.observe(this, {
            weatherForecastAdapter.setItems(it)
            weatherForecastAdapter.notifyDataSetChanged()
        })

        viewModel.ldState.observe(this, {
            when (it) {
                ProgressState.CONTENT -> {
                    hideProgress()
                }
                ProgressState.PROGRESS -> {
                    showProgress()
                }
                is ProgressState.Error -> {
                    showError(it.text, it.pictureId)
                }
            }
        })
    }

    override fun handleAction(action: String, data: Bundle?) {
        super.handleAction(action, data)

        when (action) {
            WeatherForecastViewModel.ACTION_BACK -> {
                finish()
            }
        }
    }

    private fun showProgress() {
        binding.llError.changeVisibility(false)
        binding.btnRetry.changeVisibility(false)
        binding.progressBar.changeVisibility(true)
    }

    private fun hideProgress() {
        binding.llError.changeVisibility(false)
        binding.progressBar.changeVisibility(false)
    }

    private fun showError(text: String, pictureId: Int) {
        binding.llError.changeVisibility(true)
        binding.btnRetry.changeVisibility(true)
        binding.progressBar.changeVisibility(false)

        binding.ivErrorPicture.setImageResource(pictureId)
        binding.tvErrorText.text = text
    }
}