package com.karim.photoweather.ui.preview

import android.os.Bundle
import androidx.activity.viewModels
import com.karim.photoweather.R
import com.karim.photoweather.databinding.PreviewActivityBinding
import com.karim.photoweather.extensions.onTransformationEndContainerApplyParams
import com.karim.photoweather.model.PhotoModel
import com.karim.photoweather.utils.PHOTO_EXTRA_KEY
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.bundleNonNull
import com.skydoves.bundler.intentOf
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PreviewActivity:BindingActivity<PreviewActivityBinding>(R.layout.preview_activity) {

    @Inject
    lateinit var previewViewModelFactory: PreviewViewModel.AssistedFactory

    private val photoModel:PhotoModel by bundleNonNull(PHOTO_EXTRA_KEY)

    val viewModel: PreviewViewModel by viewModels{
        PreviewViewModel.provideFactory(previewViewModelFactory, photoModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationEndContainerApplyParams()
        super.onCreate(savedInstanceState)
        binding {
            lifecycleOwner = this@PreviewActivity
            vm = viewModel
        }
        binding.deleteBtn.setOnClickListener {

        }
        binding.shareBtn.setOnClickListener {

        }
        binding.finishBtn.setOnClickListener {
            finish()
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
        finish()
    }

}