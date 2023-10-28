package com.pictures.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pictures.domain.PictureData
import com.pictures.domain.repository.PictureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val pictureRepository: PictureRepository,
) : ViewModel() {

    private val _removeItemFlow = MutableStateFlow(mutableListOf<PictureData>())
    val removedItemsFlow: Flow<List<PictureData>> = _removeItemFlow

    val pictureList: Flow<PagingData<PictureData>> =
        Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = true,
                maxSize = 20,
                initialLoadSize = 1
            )
        ) {
            pictureRepository.getPictureCachePagingSource()
        }.flow

    fun remove(item: PictureData?) {
        if (item == null) {
            return
        }

        val removes = _removeItemFlow.value
        val list = mutableListOf(item)
        list.addAll(removes)
        _removeItemFlow.value = list

        removePicture(item)
    }

    private fun removePicture(picture: PictureData) {
        pictureRepository.delete(picture.id.toInt())
    }
}