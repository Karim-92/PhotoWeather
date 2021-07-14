package com.karim.photoweather.ui.preview

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.WorkerThread
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.karim.photoweather.R
import com.karim.photoweather.databinding.PreviewActivityBinding
import com.karim.photoweather.extensions.onTransformationEndContainerApplyParams
import com.karim.photoweather.model.PhotoModel
import com.karim.photoweather.utils.PHOTO_EXTRA_KEY
import com.karim.photoweather.utils.getImageWithBanner
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.bundleNonNull
import com.skydoves.bundler.intentOf
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class PreviewActivity : BindingActivity<PreviewActivityBinding>(R.layout.preview_activity) {

    @Inject
    lateinit var previewViewModelFactory: PreviewViewModel.AssistedFactory

    private val photoModel: PhotoModel by bundleNonNull(PHOTO_EXTRA_KEY)

    val viewModel: PreviewViewModel by viewModels {
        PreviewViewModel.provideFactory(previewViewModelFactory, photoModel)
    }
    private lateinit var tempImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
//        onTransformationEndContainerApplyParams()
        super.onCreate(savedInstanceState)
        Timber.d("Photo Model: $photoModel")
        binding {
            lifecycleOwner = this@PreviewActivity
            vm = viewModel
        }
        binding.deleteBtn.setOnClickListener {
            viewModel.deleteImage(photoModel)
            super.finish()
        }
        binding.shareBtn.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    tempImageUri=onShareBtnClicked(
                        getImageWithBanner(binding.container)
                    )
                    shareImage(tempImageUri)
                }
            }
        }

        binding.finishBtn.setOnClickListener {
            super.finish()
        }
    }

    companion object {
        fun startActivity(
            transformationLayout: TransformationLayout,
            photo: PhotoModel
        ) =
            transformationLayout.context.intentOf<PreviewActivity> {
                putExtra(PHOTO_EXTRA_KEY to photo)
                TransformationCompat.startActivity(transformationLayout, intent)
            }
    }

    override fun onBackPressed() {
        super.finish()
    }

    @WorkerThread
    private fun onShareBtnClicked(bitmap: Bitmap): Uri {
        val cacheDir = File(getCacheDir(), "images")
        lateinit var uri: Uri
        var outputStream: FileOutputStream? = null
        cacheDir.mkdirs()
        val tempImage = File(cacheDir, "shared_image.png")
        try {
            outputStream = FileOutputStream(tempImage)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        } catch (e: FileNotFoundException) {
            Timber.e("Image file not found with exception ${e.localizedMessage}")
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush()
                    outputStream.fd.sync()
                    outputStream.close()
                    uri = FileProvider.getUriForFile(
                        this,
                        "com.karim.photoweather.fileprovider",
                        tempImage
                    );
                } catch (e: IOException) {
                    Timber.e("File saving failed with exception ${e.localizedMessage}")
                }
            }
        }
        return uri
    }

    private fun shareImage(imageUri:Uri){
        val intent= Intent(android.content.Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, tempImageUri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/png");
        startActivity(intent);
    }
}