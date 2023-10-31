package com.pictures.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.pictures.domain.PictureData

class PictureDiffUtilCallback : DiffUtil.ItemCallback<PictureData>() {

    override fun areItemsTheSame(oldItem: PictureData, newItem: PictureData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PictureData, newItem: PictureData): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}