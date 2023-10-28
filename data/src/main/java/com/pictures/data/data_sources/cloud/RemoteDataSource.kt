package com.pictures.data.data_sources.cloud

import com.pictures.data.network.data.Picture
import com.pictures.domain.paging_source.PictureCloudPagingSource
import retrofit2.Call

interface RemoteDataSource {
    fun getPictureCloudPagingSource(): PictureCloudPagingSource
}