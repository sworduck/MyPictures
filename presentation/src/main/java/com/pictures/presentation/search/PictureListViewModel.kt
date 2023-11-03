package com.pictures.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pictures.domain.PictureData
import com.pictures.domain.usecases.DeleteFavoritePictureUseCase
import com.pictures.domain.usecases.GetPagePictureUseCase
import com.pictures.domain.usecases.SaveFavoritePictureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureListViewModel @Inject constructor(
    private val getPagePictureUseCase: GetPagePictureUseCase,
    private val saveFavoritePictureUseCase: SaveFavoritePictureUseCase,
    private val deleteFavoritePictureUseCase: DeleteFavoritePictureUseCase,
) : ViewModel() {

    private val _pictureList =
        MutableStateFlow<PagingData<PictureData>>(PagingData.empty())
    val pictureList: Flow<PagingData<PictureData>> =
        _pictureList.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    init {
        loadPhotos()
    }

    private fun loadPhotos() {

        viewModelScope.launch {
            getPagePictureUseCase.invoke().cachedIn(viewModelScope)
                .collect {
                    _pictureList.value = it
                }
        }

    }

    fun addPicture(picture: PictureData) {
        viewModelScope.launch {
            saveFavoritePictureUseCase.invoke(picture)
        }
    }

    fun removePicture(picture: PictureData) {
        viewModelScope.launch {
            deleteFavoritePictureUseCase.invoke(picture)
        }
    }
}