package com.pictures.data.data_sources.cache

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pictures.data.database.dao.PicturesDao
import com.pictures.data.toPictureData
import com.pictures.domain.PictureData
import com.pictures.domain.paging_source.PictureCachePagingSource
import javax.inject.Inject

class PictureCachePagingSourceImpl @Inject constructor (private val pictureDao: PicturesDao): PictureCachePagingSource() {

    override fun getRefreshKey(state: PagingState<Int, PictureData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PictureData> {
        val pictureListCache =
            pictureDao.getAllPicture().map { picture -> picture.toPictureData() }
        val page = params.key ?: 1
        return if (pictureListCache.isEmpty()) {
            LoadResult.Error(Exception())//EmptyListException())
        } else {
            LoadResult.Page(
                data = pictureListCache,
                prevKey = if (page - 1 < params.loadSize - 1 && page > 0) page - 1 else null,
                nextKey = if (page + 1 < params.loadSize - 1 && page > 0) page + 1 else null
            )
        }
    }
}