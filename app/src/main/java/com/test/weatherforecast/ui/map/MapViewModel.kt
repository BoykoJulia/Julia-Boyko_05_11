package com.test.weatherforecast.ui.map

import android.app.Application
import android.location.Location
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.test.weatherforecast.R
import com.test.weatherforecast.WeatherForecastApplication
import com.test.weatherforecast.architecture.BaseViewModel

class MapViewModel(application: Application) : BaseViewModel<MapRepository>(application) {

    override fun inject() = WeatherForecastApplication.appComponent.inject(this)

    private var selectedLatLng: LatLng? = null
    val ldSearchText = MutableLiveData<String?>()

    companion object {
        const val ACTION_OPEN_WEATHER_FORECAST = "action_open_weather_forecast"
        const val PARAM_LAT_LNG_SELECTED_PLACE = "param_lat_lng_selected_place"

        const val ACTION_SET_USER_LOCATE = "action_set_user_locate"
        const val PARAM_SET_USER_LOCATE = "param_set_user_locate"
    }

    fun onButtonWeatherForecastClicked() {
        if (selectedLatLng == null) return

        val bundle = Bundle()
        bundle.putParcelable(PARAM_LAT_LNG_SELECTED_PLACE, selectedLatLng)

        ldAction.postValue(Pair(ACTION_OPEN_WEATHER_FORECAST, bundle))
    }

    fun onPlaceSelected(place: Place) {
        selectedLatLng = place.latLng
        ldSearchText.postValue(place.name)

        val bundle = Bundle()
        bundle.putParcelable(PARAM_SET_USER_LOCATE, place.latLng)

        ldAction.postValue(ACTION_SET_USER_LOCATE to bundle)
    }

    fun onCameraMoveStarted() {
        ldSearchText.postValue(null)
    }

    fun onCameraIdleListener(cameraPosition: CameraPosition) {
        selectedLatLng = cameraPosition.target
    }

    fun onFindUserLocation(location: Location?) {
        if (location == null) return

        selectedLatLng = LatLng(location.latitude, location.longitude)

        val bundle = Bundle()
        bundle.putParcelable(PARAM_SET_USER_LOCATE, selectedLatLng)

        ldAction.postValue(ACTION_SET_USER_LOCATE to bundle)
    }

    fun onPermissionLocateDenied() {
        val bundle = Bundle()
        bundle.putString(PARAM_TOAST_TEXT, getString(R.string.activity_map_permission_denied_text))

        ldAction.postValue(ACTION_SHOW_TOAST to bundle)
    }
}