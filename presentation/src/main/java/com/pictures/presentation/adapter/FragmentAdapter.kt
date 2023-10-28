package com.pictures.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.pictures.domain.PictureData
import com.pictures.presentation.databinding.PictureItemBinding

class FragmentAdapter(
    private val onFeaturedClick: (PictureData) -> Unit,
) : PagingDataAdapter<PictureData, ViewHolder>(PictureDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PictureItemBinding.inflate(
                LayoutInflater.from(parent.context),
            parent,
            false), onFeaturedClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}