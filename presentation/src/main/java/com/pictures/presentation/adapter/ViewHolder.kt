package com.pictures.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.pictures.domain.PictureData
import com.pictures.presentation.R
import com.pictures.presentation.databinding.PictureItemBinding
import com.pictures.presentation.utils.loadImage

class ViewHolder(
    private val binding: PictureItemBinding,
    private val onFeaturedClick: (PictureData) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(picture: PictureData) {
        binding.ivPhoto.loadImage(picture.downloadUrl)
        if (picture.favorite)
            binding.imageButton.setBackgroundResource(R.drawable.ic_baseline_star_rate_24)
        else
            binding.imageButton.setBackgroundResource(R.drawable.ic_baseline_star_border_24)

        binding.imageButton.setOnClickListener {
            if (picture.favorite) {
                binding.imageButton.setBackgroundResource(R.drawable.ic_baseline_star_border_24)
            } else {
                binding.imageButton.setBackgroundResource(R.drawable.ic_baseline_star_rate_24)
            }
            onFeaturedClick(picture)
        }
    }
}