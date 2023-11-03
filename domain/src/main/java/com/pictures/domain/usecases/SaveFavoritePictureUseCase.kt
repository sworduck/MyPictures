package com.pictures.domain.usecases

import com.pictures.domain.PictureData
import com.pictures.domain.repository.PictureRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveFavoritePictureUseCase @Inject constructor(
    private val pictureRepository: PictureRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(pictureData: PictureData) {
        return withContext(ioDispatcher) {
            pictureRepository.savePicture(pictureData)
        }
    }
}