package com.pictures.data.data_sources.cloud

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pictures.data.network.NetworkConstant
import com.pictures.data.network.retrofit.PictureApi
import com.pictures.domain.PictureData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: PictureApi
): RemoteDataSource {

    override fun getPhotos(): Flow<PagingData<PictureData>> {
        return Pager(
            config = PagingConfig(
                pageSize = NetworkConstant.NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                PictureCloudPagingSourceImpl(
                    service = api,
                )
            }
        ).flow
    }
}