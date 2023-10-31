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
        changeUiFavorite(picture.favorite)

        binding.imageButton.setOnClickListener {
            changeUiFavorite(picture.favorite.not())
            onFeaturedClick(picture)
            picture.favorite = picture.favorite.not()
        }
    }

    private fun changeUiFavorite(favorite: Boolean){
        if (favorite)
            binding.imageButton.setBackgroundResource(R.drawable.ic_baseline_star_rate_24)
        else
            binding.imageButton.setBackgroundResource(R.drawable.ic_baseline_star_border_24)
    }
}