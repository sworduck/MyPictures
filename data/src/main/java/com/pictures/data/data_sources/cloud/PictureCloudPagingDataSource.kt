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

class PictureCloudPagingSourceImpl @Inject constructor (private val service: PictureApi) : PictureCloudPagingSource() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PictureData> {
        val position = params.key ?: STARTING_KEY
        return try {
            val photos = service.getPictureList(position,NETWORK_PAGE_SIZE).map { it.toPictureData() }
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
            // This loads starting from previous page, but since PagingConfig.initialLoadSize spans
            // multiple pages, the initial load will still load items centered around
            // anchorPosition. This also prevents needing to immediately launch prepend due to
            // prefetchDistance.
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    /*override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PictureData> {
        // If params.key is null, it is the first load, so we start loading with STARTING_KEY
        try {
            val startKey = params.key ?: STARTING_KEY

            // We fetch as many articles as hinted to by params.loadSize
            val range = startKey.until(startKey + params.loadSize)

            val pictureCloud =
                service.getPictureList(startKey, 10).map { it.toPictureData() }
            pictureCloud.forEach {
                if(it.id.toInt() in PictureRepository.pictureListIdFromCache) {
                    it.favorite = true
                }
            }
            return LoadResult.Page(
                data = pictureCloud,
                prevKey = when (startKey) {
                    STARTING_KEY -> null
                    else -> when (val prevKey =
                        ensureValidKey(key = range.first - params.loadSize)) {
                        // We're at the start, there's nothing more to load
                        STARTING_KEY -> null
                        else -> prevKey
                    }
                },
                nextKey = range.last + 10
            )
        }catch (e:Exception){
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PictureData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(10)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(10)
        }
    }

    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)*/
}