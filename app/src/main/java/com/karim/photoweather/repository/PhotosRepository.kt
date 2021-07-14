package com.karim.photoweather.repository

import androidx.annotation.WorkerThread
import com.karim.photoweather.model.PhotoModel
import com.karim.photoweather.network.WeatherClient
import com.karim.photoweather.persistence.PhotosDao
import com.karim.photoweather.utils.deleteImageFromDisk
import com.skydoves.whatif.whatIfNotNull
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class PhotosRepository @Inject constructor(
    private val weatherClient: WeatherClient,
    private val photosDao: PhotosDao
) {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    @WorkerThread
    fun getWeatherInfo(
        lat: Double,
        lon: Double,
        onStart: () -> Unit,
        onComplete: () -> Unit
    ) = flow {
        val weatherInfo = weatherClient.getWeatherDetails(lat, lon)
        weatherInfo.whatIfNotNull {
            Timber.d("Weather Icon: ${weatherInfo.weather[0].iconPath}")
            emit(weatherInfo)
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(dispatcher)

    @WorkerThread
    suspend fun insertPhoto(photo: PhotoModel) {
        return withContext(dispatcher) {
            photosDao.insertPhoto(photo)
        }
    }

    @WorkerThread
    suspend fun deletePhotoFromDb(photo: PhotoModel) {
        withContext(dispatcher) {
            deleteImageFromDisk(photo.path)
            photosDao.deletePhotos(photo)
        }
    }

    @WorkerThread
    fun getPhotosFromDb() = flow {
        val photosList = photosDao.getAllPhotos()
        photosList.whatIfNotNull {
            Timber.d("Photo list size: ${photosList.size}")
            emit(photosList)
        }
    }.flowOn(Dispatchers.IO)

}