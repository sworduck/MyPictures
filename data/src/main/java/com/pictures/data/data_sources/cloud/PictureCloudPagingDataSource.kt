package com.pictures.data.data_sources.cloud

import androidx.paging.PagingState
import com.pictures.data.network.NetworkConstant.Companion.NETWORK_PAGE_SIZE
import com.pictures.data.network.NetworkConstant.Companion.STARTING_KEY
import com.pictures.data.network.retrofit.PictureApi
import com.pictures.data.toPictureData
import com.pictures.domain.PictureData
import com.pictures.domain.paging_source.PictureCloudPagingSource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class PictureCloudPagingSourceImpl @Inject constructor(
    private val service: PictureApi,
) : PictureCloudPagingSource() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PictureData> {
        val position = params.key ?: STARTING_KEY
        return try {
            val response = service.getPictureList(position, NETWORK_PAGE_SIZE)
            val photos = response.body()!!.let {
                it.map { picture ->
                    picture.toPictureData()
                }
            }
            LoadResult.Page(
                data = photos,
                prevKey = if (position == STARTING_KEY) null else position,
                nextKey = if (photos.isEmpty()) null else position + 1

            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PictureData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}