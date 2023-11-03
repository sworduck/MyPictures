package com.pictures.data.data_sources.cloud

import androidx.paging.PagingData
import com.pictures.domain.PictureData
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun getPhotos(): Flow<PagingData<PictureData>>
}