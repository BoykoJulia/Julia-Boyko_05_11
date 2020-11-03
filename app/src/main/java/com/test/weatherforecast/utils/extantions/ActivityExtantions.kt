package com.test.weatherforecast.utils.extantions

import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.setupFullScreen() {
    window.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
}

fun AppCompatActivity.setupLightStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        window.decorView.systemUiVisibility =
            window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}
