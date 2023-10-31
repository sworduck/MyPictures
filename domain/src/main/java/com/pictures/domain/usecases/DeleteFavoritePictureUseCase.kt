package com.pictures.domain.usecases

import com.pictures.domain.PictureData
import com.pictures.domain.repository.PictureRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteFavoritePictureUseCase @Inject constructor(
    private val pictureRepository: PictureRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(pictureData: PictureData): Result<Unit> {
        return withContext(ioDispatcher) {
            pictureRepository.delete(pictureData.id.toInt())
        }
    }
}