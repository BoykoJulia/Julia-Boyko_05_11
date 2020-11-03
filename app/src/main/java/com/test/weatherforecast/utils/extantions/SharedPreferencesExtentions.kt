package com.test.weatherforecast.utils.extantions

import android.content.SharedPreferences

fun SharedPreferences.putString(key: String, value: String?) {
    this.edit().putString(key, value).apply()
}

fun SharedPreferences.putInt(key: String, value: Int?) {
    if (value == null) {
        this.edit().remove(key).apply()
    } else {
        this.edit().putInt(key, value).apply()
    }
}

fun SharedPreferences.putLong(key: String, value: Long?) {
    if (value == null) {
        this.edit().remove(key).apply()
    } else {
        this.edit().putLong(key, value).apply()
    }
}

fun SharedPreferences.putBoolean(key: String, value: Boolean) {
    this.edit().putBoolean(key, value).apply()
}