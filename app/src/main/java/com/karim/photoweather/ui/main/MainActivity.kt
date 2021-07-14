package com.karim.photoweather.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.location.*
import com.karim.photoweather.R
import com.karim.photoweather.databinding.MainActivityBinding
import com.karim.photoweather.model.PhotoModel
import com.karim.photoweather.ui.adapter.PhotosAdapter
import com.karim.photoweather.ui.preview.PreviewActivity
import com.karim.photoweather.utils.PHOTO_EXTRA_KEY
import com.karim.photoweather.utils.PermissionConstants.PERMISSIONS
import com.karim.photoweather.utils.PermissionConstants.REQUEST_CODE_PERMISSION
import com.karim.photoweather.utils.REQUEST_CHECK_SETTINGS
import com.karim.photoweather.utils.UserLocation
import com.karim.photoweather.utils.UserLocationInterface
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.intentOf
import com.skydoves.transformationlayout.onTransformationStartContainer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : BindingActivity<MainActivityBinding>(R.layout.main_activity),
    EasyPermissions.PermissionCallbacks, UserLocationInterface {

    private val viewModel: MainViewModel by viewModels()
    private val photosAdapter = PhotosAdapter()
    private lateinit var userLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val locationManager: UserLocation by lazy {
        UserLocation(this@MainActivity, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getUserPermissions()

        binding {
            lifecycleOwner = this@MainActivity
            adapter = photosAdapter
            vm = viewModel
        }

        startLocationQueries()

        binding.noImages.enableMergePathsForKitKatAndAbove(true)

        binding.cameraButton.setOnClickListener {
            captureImage()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getUserPermissions() {
        val context = this
        if (EasyPermissions.hasPermissions(context, *PERMISSIONS)) {
            // Already have permission, do the thing
            Timber.d("GetUserPermissions: Permissions granted successfully!")
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        userLocation = location
                    }
                }
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                this, getString(R.string.permissions_label),
                REQUEST_CODE_PERMISSION, *PERMISSIONS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @SuppressLint("MissingPermission")
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Timber.d("onPermissionsGranted: Permissions granted successfully!")
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    userLocation = location
                }
            }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Timber.d("onPermissionsDenied:$requestCode : ${perms.size}")
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    @SuppressLint("MissingPermission")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                startLocationQueries()
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        if (location != null) {
                            userLocation = location
                        }
                    }
            }
        } else {
            if (resultCode == Activity.RESULT_OK) {
                val uri: Uri = data?.data!!
                val photo: PhotoModel = PhotoModel(
                    path = uri.path.toString(),
                    lat = userLocation.latitude,
                    lon = userLocation.longitude
                )
                Timber.d("Image URI: ${uri.path}")
                Timber.d("Userlocation: $userLocation")
                viewModel.insertPhoto(photo)
                startPreviewActivity(photo)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun captureImage() {
        ImagePicker.with(this).saveDir(getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!)
            .cameraOnly().start()
    }

    override fun onStop() {
        super.onStop()
        locationManager.stopLocationUpdates()
    }

    override fun onLocationRetrieved(location: Location?) {
        if (location != null) {
            userLocation = location
        }
    }

    fun startPreviewActivity(photo: PhotoModel) {
        intentOf<PreviewActivity> {
            putExtra(PHOTO_EXTRA_KEY to photo)
            startActivity(this@MainActivity)
        }
    }

    fun startLocationQueries() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO)
            {
                locationManager.startLocationUpdates()
            }
        }
    }

    override fun onResume() {
        if (!locationManager.requestingLocationUpdate) {
            startLocationQueries()
        }
        viewModel.resetViewModel()
        super.onResume()
    }

}