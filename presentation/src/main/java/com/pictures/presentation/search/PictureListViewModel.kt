package com.pictures.presentation.search

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pictures.domain.PictureData
import com.pictures.domain.repository.PictureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PictureListViewModel @Inject constructor(
    private val pictureRepository: PictureRepository,
) : ViewModel() {

    val pictureList: Flow<PagingData<PictureData>> =
        Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = true,
                maxSize = 50,
                initialLoadSize = 1
            )
        ) {
            pictureRepository.getPictureCloudPagingSource()
        }.flow

    fun addPicture(picture: PictureData) {
        pictureRepository.savePicture(picture)
    }

    fun removePicture(picture: PictureData) {
        pictureRepository.delete(picture.id.toInt())
    }
}