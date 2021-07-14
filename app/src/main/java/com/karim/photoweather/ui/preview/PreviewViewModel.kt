package com.karim.photoweather.ui.preview

import androidx.databinding.Bindable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.karim.photoweather.model.PhotoModel
import com.karim.photoweather.model.WeatherModel
import com.karim.photoweather.repository.PhotosRepository
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.asBindingProperty
import com.skydoves.bindables.bindingProperty
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class PreviewViewModel @AssistedInject constructor(
    private val photosRepository: PhotosRepository,
    @Assisted private val photoModel: PhotoModel
) : BindingViewModel() {

    private val weatherDetails: MutableStateFlow<Int> = MutableStateFlow(0)

    @get:Bindable
    var isLoading: Boolean by bindingProperty(false)
        private set

    private var weatherFlow = weatherDetails.flatMapLatest {
        photosRepository.getWeatherInfo(photoModel.lat,
            photoModel.lon,
            onStart = { isLoading = true },
            onComplete = { isLoading = false })
    }

    @get:Bindable
    val weatherInfo: WeatherModel? by weatherFlow.asBindingProperty(viewModelScope, null)

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(photoModel: PhotoModel): PreviewViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            photoModel: PhotoModel
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(photoModel) as T
            }
        }
    }
}