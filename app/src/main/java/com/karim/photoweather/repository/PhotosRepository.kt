package com.karim.photoweather.repository

import android.graphics.Bitmap
import androidx.annotation.WorkerThread
import com.karim.photoweather.model.PhotoModel
import com.karim.photoweather.model.WeatherModel
import com.karim.photoweather.network.WeatherClient
import com.karim.photoweather.persistence.PhotosDao
import com.karim.photoweather.utils.deleteImageFromDisk
import com.karim.photoweather.utils.saveNewImageWithBanner
import com.skydoves.whatif.whatIfNotNull
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class PhotosRepository @Inject constructor(
    private val weatherClient: WeatherClient,
    private val photosDao: PhotosDao
){
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    @WorkerThread
    fun getWeatherInfo(
        lat: Double,
        lon: Double
    )=flow {
        val weatherInfo = weatherClient.getWeatherDetails(lat, lon)
        weatherInfo.whatIfNotNull {
            emit(weatherInfo)
        }
    }.flowOn(dispatcher)

    @WorkerThread
    suspend fun insertPhoto(photo: PhotoModel){
        return withContext(dispatcher){
            photosDao.insertPhoto(photo)
        }
    }

    @WorkerThread
    suspend fun deletePhotoFromDb(photo: PhotoModel){
        withContext(dispatcher){
            deleteImageFromDisk(photo.path)
            photosDao.deletePhotos(photo)
        }
    }

    @WorkerThread
    fun getPhotosFromDb() = flow {
        val photosList = photosDao.getAllPhotos()
        photosList.whatIfNotNull {
            emit(photosList)
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun savePhotoWithBanner(path: String, bitmap: Bitmap){
        saveNewImageWithBanner(path, bitmap)
    }

}