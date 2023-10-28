package com.pictures.domain.usecases

import com.pictures.domain.repository.PictureRepository
import javax.inject.Inject

class GetPagePictureUseCase (
    private val pictureRepository: PictureRepository
) {
    fun getPagePicture(page: Int) {
        pictureRepository.getAllPicture()
    }
}