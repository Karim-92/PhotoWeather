package com.karim.photoweather.utils

import android.location.Location

interface UserLocationInterface {
    fun onLocationRetrieved(location: Location?)
}