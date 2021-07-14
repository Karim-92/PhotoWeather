package com.karim.photoweather.ui.main

import androidx.databinding.Bindable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.karim.photoweather.model.PhotoModel
import com.karim.photoweather.repository.PhotosRepository
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.asBindingProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val photosRepository: PhotosRepository,
    private val savedStateHandle: SavedStateHandle
) : BindingViewModel() {

    private val photosStateFlow: MutableStateFlow<Int> = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val photosListFlow = photosStateFlow.flatMapLatest {
        photosRepository.getPhotosFromDb()
    }

    @get:Bindable
    val photosList: List<PhotoModel> by photosListFlow.asBindingProperty(
        viewModelScope,
        emptyList()
    )

    fun insertPhoto(photo: PhotoModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Timber.d("Photo in ViewModel: $photo")
                photosRepository.insertPhoto(photo)
            }
        }
    }
}