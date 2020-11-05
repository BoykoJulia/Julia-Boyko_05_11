package com.test.weatherforecast.ui.weather_forecast

import android.app.Application
import androidx.annotation.DrawableRes
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.test.weatherforecast.R
import com.test.weatherforecast.WeatherForecastApplication
import com.test.weatherforecast.architecture.BaseViewModel
import com.test.weatherforecast.data.Forecast
import com.test.weatherforecast.utils.InternetUnreachableException
import com.test.weatherforecast.utils.extantions.applySchedulers
import com.test.weatherforecast.utils.extantions.checkInternetConnection

class WeatherForecastViewModel(
    application: Application,
    private val selectedLatLng: LatLng?
) : BaseViewModel<WeatherForecastRepository>(application) {

    companion object {
        const val ACTION_BACK = "action_back"
    }

    override fun inject() = WeatherForecastApplication.appComponent.inject(this)

    val ldWeatherForecastItems = MutableLiveData<ArrayList<Forecast>>()
    val ldLocationTitle = MutableLiveData<String>()
    val ldState = MutableLiveData<ProgressState>()

    init {
        loadData()
    }

    private fun loadData() {
        showProgress()
        val disposable = repository.loadWeatherForecast(selectedLatLng)
            .checkInternetConnection(getApplication())
            .applySchedulers()
            .subscribe({
                hideProgress()

                ldWeatherForecastItems.postValue(it.forecasts)
                ldLocationTitle.postValue(it.locationName)
            }, {
                it.printStackTrace()

                when(it) {
                    is InternetUnreachableException -> {
                        showError(getString(R.string.error_no_internet_connection), R.drawable.error)
                    }
                    else -> {
                        showError(getString(R.string.error_default), R.drawable.error)
                    }
                }
            })

        compositeDisposable.add(disposable)
    }

    fun onButtonRetryClicked() {
        loadData()
    }

    fun onBackButtonClicked() {
        ldAction.postValue(ACTION_BACK to null)
    }

    private fun showProgress() {
        ldState.postValue(ProgressState.PROGRESS)
    }

    private fun hideProgress() {
        ldState.postValue(ProgressState.CONTENT)
    }

    private fun showError(errorMessage: String, pictureId: Int) {
        ldState.postValue(ProgressState.Error(errorMessage, pictureId))
    }
}

sealed class ProgressState {
    object PROGRESS : ProgressState()
    object CONTENT : ProgressState()
    class Error(val text: String, @DrawableRes val pictureId: Int) : ProgressState()
}