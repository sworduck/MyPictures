package com.pictures.data.data_sources.cloud

import com.pictures.data.network.data.Picture
import com.pictures.data.network.retrofit.PictureApi
import com.pictures.domain.paging_source.PictureCloudPagingSource
import retrofit2.Call
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val retrofitApi: PictureApi):
    RemoteDataSource {
    override fun getPictureCloudPagingSource(): PictureCloudPagingSource {
        return PictureCloudPagingSourceImpl(retrofitApi)
    }
}