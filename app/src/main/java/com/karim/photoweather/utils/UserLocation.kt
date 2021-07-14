package com.karim.photoweather.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

/**
 * A helper class used to calculate the user's current location. An alternative would be to get
 * the location using the IP of the user via a third party network call since
 * we don't really care about the exact location of the user.
 */
class UserLocation(
    val activity: Activity,
    private val userLocationInterface: UserLocationInterface
) {
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mSettingsClient: SettingsClient? = null
    private lateinit var mLocationRequest: LocationRequest
    private var mLocationSettingsRequest: LocationSettingsRequest? = null
    private var mLocationCallback: LocationCallback? = null

    init {
        setupLocationService()
    }

    private fun setupLocationService() {
        // init location clients.
        mFusedLocationClient =
            LocationServices.getFusedLocationProviderClient(activity.applicationContext)
        mSettingsClient = LocationServices.getSettingsClient(activity.applicationContext)
        createLocationCallback()
        mLocationRequest = createLocationRequest()
        buildLocationSettingsRequest()
    }

    private fun createLocationCallback() {
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                userLocationInterface.onLocationRetrieved(locationResult.lastLocation)
            }
        }
    }

    private fun buildLocationSettingsRequest() {
        val builder = LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest)
        builder.addLocationRequest(mLocationRequest)
        mLocationSettingsRequest = builder.build()
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        mSettingsClient?.checkLocationSettings(mLocationSettingsRequest)
            ?.addOnSuccessListener(activity) {
                if (ActivityCompat.checkSelfPermission(
                        activity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(
                        activity,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return@addOnSuccessListener
                }
                mFusedLocationClient?.requestLocationUpdates(
                    mLocationRequest,
                    mLocationCallback, Looper.myLooper()
                )
            }
    }


    fun stopLocationUpdates() {
        mFusedLocationClient?.removeLocationUpdates(mLocationCallback)
        mFusedLocationClient = null
        mLocationCallback = null
    }

    private fun createLocationRequest() = LocationRequest.create().apply {
        interval = 10000
        fastestInterval = 1000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

}