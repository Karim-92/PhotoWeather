package com.karim.photoweather.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.karim.photoweather.model.PhotoModel

@Database(entities=[PhotoModel::class], version = 1 , exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photosDao():PhotosDao
}