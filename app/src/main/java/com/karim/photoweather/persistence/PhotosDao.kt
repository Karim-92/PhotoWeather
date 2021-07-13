package com.karim.photoweather.persistence

import androidx.room.*
import com.karim.photoweather.model.PhotoModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photoModel: PhotoModel)

    @Query("SELECT * FROM photos")
    fun getAllPhotos(): List<PhotoModel>

    @Delete
    suspend fun deletePhotos(photo: PhotoModel) : Int

}