package com.karim.photoweather.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "photos")
@Parcelize
data class PhotoModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = 0,
    var path: String ="",
    var lat: Double? = 0.0,
    var long: Double? = 0.0
) : Parcelable