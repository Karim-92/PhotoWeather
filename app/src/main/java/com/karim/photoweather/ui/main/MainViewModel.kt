package com.karim.photoweather.ui.main

import androidx.databinding.Bindable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.karim.photoweather.model.PhotoModel
import com.karim.photoweather.repository.PhotosRepository
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.asBindingProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val photosRepository: PhotosRepository,
    private val savedStateHandle: SavedStateHandle
): BindingViewModel()  {

    private val photosStateFlow: MutableStateFlow<Int> = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val photosListFlow = photosStateFlow.flatMapLatest {
        photosRepository.getPhotosFromDb()
    }

    @get:Bindable
    val photosList:List<PhotoModel> by photosListFlow.asBindingProperty(
        viewModelScope,
        emptyList()
    )

    fun insertPhoto(photoPath:String){
        viewModelScope.launch {
            var photoModel:PhotoModel = PhotoModel(path = photoPath)
            // TODO: Get latitude and longitude, get weather info for the image and start preview with things bundled from mainactivity
            photosRepository.insertPhoto(photoModel)
        }
    }
}