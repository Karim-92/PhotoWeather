package com.karim.photoweather.ui.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.viewModels
import com.github.dhaval2404.imagepicker.ImagePicker
import com.karim.photoweather.R
import com.karim.photoweather.databinding.MainActivityBinding
import com.karim.photoweather.model.PhotoModel
import com.karim.photoweather.ui.adapter.PhotosAdapter
import com.karim.photoweather.utils.PermissionConstants.PERMISSIONS
import com.karim.photoweather.utils.PermissionConstants.REQUEST_CODE_PERMISSION
import com.skydoves.bindables.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber
import java.io.File


@AndroidEntryPoint
class MainActivity : BindingActivity<MainActivityBinding>(R.layout.main_activity) , EasyPermissions.PermissionCallbacks{

    private val viewModel: MainViewModel by viewModels()
    private val photosAdapter = PhotosAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding{
            lifecycleOwner=this@MainActivity
            adapter=photosAdapter
            vm=viewModel
        }
        getUserPermissions()

        binding.cameraButton.setOnClickListener {
            captureImage()
        }
    }

    private fun getUserPermissions() {
        val context = this
        if (EasyPermissions.hasPermissions(context, *PERMISSIONS)) {
            // Already have permission, do the thing
            Timber.d("GetUserPermissions: Permissions granted successfully!")
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

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Timber.d("onPermissionsGranted: Permissions granted successfully!")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Timber.d("onPermissionsDenied:$requestCode : ${perms.size}")
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            viewModel.insertPhoto(uri.path!!)
            Timber.d("Image URI: ${uri.path}")
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    fun captureImage(){
        ImagePicker.with(this).saveDir(getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!)
            .cameraOnly().start()
    }

}