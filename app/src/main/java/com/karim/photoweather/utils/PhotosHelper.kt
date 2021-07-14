package com.karim.photoweather.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import com.skydoves.whatif.whatIfNotNull
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

fun getImageWithBanner(view: View): Bitmap {
    val bitmap = Bitmap.createBitmap(
        view.width,
        view.height, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}

fun deleteImageFromDisk(photoPath: String) {
    val file = File(photoPath)
    file.whatIfNotNull {
        file.delete()
    }
}
