package com.pictures.domain.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pictures.domain.PictureData

abstract class PictureCachePagingSource: PagingSource<Int, PictureData>() {

    abstract override fun getRefreshKey(state: PagingState<Int, PictureData>): Int?

    abstract override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PictureData>
}