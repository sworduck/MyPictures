package com.pictures.domain.usecases

import com.pictures.domain.repository.PictureRepository
import javax.inject.Inject

class GetFavoriteUseCase (
    private val pictureRepository: PictureRepository
) {
    fun getFavoritePictures(){
        pictureRepository.getFavoritePictures()
    }
}