package com.pictures.domain.usecases

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.pictures.domain.PictureData
import com.pictures.domain.repository.PictureRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPagePictureUseCase @Inject constructor(
    private val pictureRepository: PictureRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {
    fun invoke(): Flow<PagingData<PictureData>> {
        return pictureRepository.getPhotos()
            .cachedIn(scope = CoroutineScope(ioDispatcher))
            .combine(pictureRepository.getAllFavoritePictureId()) { pagingData, picturesId ->
                pagingData.map { picture ->
                    if (picture.id.toInt() in picturesId)
                        picture.copy(favorite = true) else picture
                }
            }
            .flowOn(ioDispatcher)
    }
}