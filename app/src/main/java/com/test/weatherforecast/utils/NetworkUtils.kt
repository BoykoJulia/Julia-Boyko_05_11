package com.test.weatherforecast.utils

import android.content.Context
import android.net.ConnectivityManager
import io.reactivex.Single

class NetworkUtils {

    companion object {
        fun isInternetAvailable(context: Context?): Boolean {
            if (context == null) return false

            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = manager.activeNetworkInfo
            return info != null && info.isConnected
        }
    }
}
