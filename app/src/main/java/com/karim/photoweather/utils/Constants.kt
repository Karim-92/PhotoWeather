package com.karim.photoweather.utils

import android.Manifest

const val ICON_URL="http://openweathermap.org/img/wn/"
const val WEATHER_URL="http://api.openweathermap.org/data/2.5/weather"

object PermissionConstants {
    const val REQUEST_CODE_PERMISSION = 100
    val PERMISSIONS =
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
}