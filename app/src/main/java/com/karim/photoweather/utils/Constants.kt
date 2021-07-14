package com.karim.photoweather.utils

import android.Manifest

const val ICON_URL="https://openweathermap.org/img/wn/"
const val WEATHER_URL="https://api.openweathermap.org/data/2.5/"
const val PHOTO_EXTRA_KEY="PHOTO"
const val TRANSFORMTION_PARAMS="com.skydoves.transformationlayout"
const val REQUEST_CHECK_SETTINGS=6


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