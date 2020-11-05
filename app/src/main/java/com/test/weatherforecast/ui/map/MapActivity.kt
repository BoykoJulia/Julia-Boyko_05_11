package com.test.weatherforecast.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.test.weatherforecast.R
import com.test.weatherforecast.architecture.binding.BaseBindingActivity
import com.test.weatherforecast.databinding.ActivityMapBinding
import com.test.weatherforecast.ui.weather_forecast.WeatherForecastActivity
import com.test.weatherforecast.utils.extantions.setupFullScreen
import com.test.weatherforecast.utils.extantions.setupLightStatusBar

private const val LOCATION_PERMISSION_REQUEST_CODE = 111
private const val GOOGLE_MAP_ZOOM = 10f

class MapActivity : BaseBindingActivity<MapViewModel, ActivityMapBinding>() {

    override val layoutId = R.layout.activity_map

    private lateinit var googleMap: GoogleMap
    private lateinit var placesClient: PlacesClient

    private var autocompleteFragment: AutocompleteSupportFragment? = null
    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    override fun setDataToBinding() {
        binding.viewModel = viewModel
    }

    override fun createViewModel(): MapViewModel {
        return provideViewModel { MapViewModel(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupFullScreen()
        setupLightStatusBar()

        initViews()

        initPlacesClient()
        requestPermissionForLocate()
    }

    private fun initViews() {
        initMapFragment()
        initAutocompleteSupportFragment()

        binding.btnWeatherForecast.setOnClickListener {
            viewModel.onButtonWeatherForecastClicked()
        }
    }

    override fun observeData() {
        super.observeData()

        viewModel.ldSearchText.observe(this, {
            autocompleteFragment?.setText(it)
        })
    }

    private fun requestPermissionForLocate() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                viewModel.onFindUserLocation(location)
            }
        }
    }

    private fun initMapFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(onMapReadyCallback)
    }

    private fun initPlacesClient() {
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, getString(R.string.google_maps_key))
        }

        placesClient = Places.createClient(this)
    }

    private fun initAutocompleteSupportFragment() {
        autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as AutocompleteSupportFragment?

        autocompleteFragment?.apply {
            setHint(getString(R.string.map_autocomplete_search_hint))
            setTypeFilter(TypeFilter.CITIES)
            setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
            setOnPlaceSelectedListener(onPlaceSelectedListener)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                val permissionResult = ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )

                if (permissionResult == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location: Location? ->
                            viewModel.onFindUserLocation(location)
                        }
                } else {
                    viewModel.onPermissionLocateDenied()
                }
            }
        }
    }

    override fun handleAction(action: String, data: Bundle?) {
        super.handleAction(action, data)

        when (action) {
            MapViewModel.ACTION_OPEN_WEATHER_FORECAST -> {
                val selectedLatLng = data?.get(MapViewModel.PARAM_LAT_LNG_SELECTED_PLACE) as LatLng
                startActivity(WeatherForecastActivity.newIntent(this, selectedLatLng))
            }
            MapViewModel.ACTION_SET_USER_LOCATE -> {
                val userLocate = data?.get(MapViewModel.PARAM_SET_USER_LOCATE) as LatLng

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocate, GOOGLE_MAP_ZOOM))
            }
        }
    }

    private val onMapReadyCallback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap

        googleMap.setOnCameraMoveStartedListener {
            viewModel.onCameraMoveStarted()
        }

        googleMap.setOnCameraIdleListener {
            viewModel.onCameraIdleListener(googleMap.cameraPosition)
        }
    }

    private val onPlaceSelectedListener = object : PlaceSelectionListener {
        override fun onPlaceSelected(place: Place) {
            viewModel.onPlaceSelected(place)
        }

        override fun onError(status: Status) {
            Log.e("ERROR", "An error occurred: $status")
        }
    }
}