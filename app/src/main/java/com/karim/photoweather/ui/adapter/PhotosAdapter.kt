package com.karim.photoweather.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.karim.photoweather.R
import com.karim.photoweather.databinding.ItemPhotoLayoutBinding
import com.karim.photoweather.model.PhotoModel
import com.skydoves.bindables.BindingListAdapter
import com.skydoves.bindables.binding

class PhotosAdapter() : BindingListAdapter<PhotoModel, PhotosAdapter.PhotoViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = parent.binding<ItemPhotoLayoutBinding>(R.layout.item_photo_layout)
        return PhotoViewHolder(binding).apply{
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition.takeIf { it!=NO_POSITION } ?: return@setOnClickListener
                // TODO: Implement the transition to the preview activity with the banner on.
            }
        }
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.binding.apply {
            photoData = getItem(position)
            executePendingBindings()
        }
    }

    class PhotoViewHolder(val binding: ItemPhotoLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<PhotoModel>() {

            override fun areItemsTheSame(
                oldItem: PhotoModel,
                newItem: PhotoModel
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: PhotoModel,
                newItem: PhotoModel
            ): Boolean =
                oldItem == newItem
        }
    }
}