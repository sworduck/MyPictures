package com.pictures.domain.repository

import androidx.paging.PagingData
import com.pictures.domain.PictureData
import com.pictures.domain.paging_source.PictureCachePagingSource
import com.pictures.domain.paging_source.PictureCloudPagingSource
import kotlinx.coroutines.flow.Flow


interface PictureRepository {

    companion object {
        val pictureListIdFromCache: ArrayList<Int> = arrayListOf()
    }

    fun getPictureCloudPagingSource(): PictureCloudPagingSource

    fun getPictureCachePagingSource(): PictureCachePagingSource

    fun savePicture(picture: PictureData)

    fun getFavoritePictures(): List<PictureData>

    fun changeFavorites(id: Int, favorites: Boolean)

    fun getAllPicture(): List<PictureData>

    fun insert(pictureEntity: PictureData)

    fun delete(id: Int)

    fun deleteAll()
}