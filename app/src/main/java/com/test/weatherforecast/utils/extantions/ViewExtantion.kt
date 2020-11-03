package com.test.weatherforecast.utils.extantions

import android.view.View

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.changeVisibility(visible: Boolean) {
    if (visible) {
        this.show()
    } else {
        this.hide()
    }
}