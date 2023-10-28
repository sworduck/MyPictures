package com.pictures.domain.usecases

import com.pictures.domain.repository.PictureRepository
import javax.inject.Inject

class ChangeFavoritesUseCase (
    private val pictureRepository: PictureRepository
) {
    fun changeFavorites(id: Int, favorites: Boolean) {
        pictureRepository.changeFavorites(id, favorites)
    }
}