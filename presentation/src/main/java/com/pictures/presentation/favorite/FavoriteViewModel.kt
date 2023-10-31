package com.pictures.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pictures.domain.PictureData
import com.pictures.domain.usecases.DeleteFavoritePictureUseCase
import com.pictures.domain.usecases.GetFavoritePictureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteUseCase: GetFavoritePictureUseCase,
    private val deleteFavoritePictureUseCase: DeleteFavoritePictureUseCase,
) : ViewModel() {

    private val _pictureList =
        MutableStateFlow<PagingData<PictureData>>(PagingData.empty())
    val pictureList: Flow<PagingData<PictureData>> =
        _pictureList.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())

    init {
        loadPhotos()
    }

    private fun loadPhotos() {
        viewModelScope.launch {
            getFavoriteUseCase.invoke().cachedIn(viewModelScope).collect {
                _pictureList.value = it
            }
        }
    }

    fun remove(item: PictureData) {
        viewModelScope.launch {
            deleteFavoritePictureUseCase.invoke(item)
        }
    }
}