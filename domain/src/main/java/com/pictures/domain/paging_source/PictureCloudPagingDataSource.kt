package com.pictures.domain.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pictures.domain.PictureData
import com.pictures.domain.repository.PictureRepository
import kotlin.math.max

abstract class PictureCloudPagingSource : PagingSource<Int, PictureData>() {

    companion object {
        private const val STARTING_KEY = 1
    }

    abstract override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PictureData>

    abstract override fun getRefreshKey(state: PagingState<Int, PictureData>): Int?

    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
}