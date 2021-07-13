package com.karim.photoweather.binding

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.io.File

object ViewBinding {
    @JvmStatic
    @BindingAdapter("resourceImage")
    fun bindLoadImage(view: AppCompatImageView, uri: String) {
        Glide.with(view.context)
            .load(File(uri))
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("gone")
    fun bindGone(view: View, shouldBeGone: Boolean) {
        view.visibility = if (shouldBeGone) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}