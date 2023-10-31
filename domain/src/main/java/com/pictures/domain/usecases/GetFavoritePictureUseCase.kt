package com.pictures.domain.usecases

import androidx.paging.PagingData
import com.pictures.domain.PictureData
import com.pictures.domain.repository.PictureRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFavoritePictureUseCase @Inject constructor(
    private val pictureRepository: PictureRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    fun invoke(): Flow<PagingData<PictureData>> {
        return pictureRepository.getFavoritePictures().flowOn(ioDispatcher)
    }
}