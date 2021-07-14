package com.karim.photoweather.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "photos")
@Parcelize
data class PhotoModel(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var path: String ="",
    var lat: Double = 0.0,
    var lon: Double = 0.0
) : Parcelable