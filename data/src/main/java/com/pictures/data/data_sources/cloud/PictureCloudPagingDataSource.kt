package com.pictures.data.data_sources.cloud

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pictures.data.network.retrofit.PictureApi
import com.pictures.data.toPictureData
import com.pictures.domain.PictureData
import com.pictures.domain.paging_source.PictureCloudPagingSource
import com.pictures.domain.repository.PictureRepository
import javax.inject.Inject
import kotlin.math.max

class PictureCloudPagingSourceImpl @Inject constructor (private val service: PictureApi) : PictureCloudPagingSource() {

    companion object {
        private const val STARTING_KEY = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PictureData> {
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

    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
}