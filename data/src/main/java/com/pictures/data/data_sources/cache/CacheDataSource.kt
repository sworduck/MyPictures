package com.pictures.data.data_sources.cache

import com.pictures.data.database.entity.PictureEntity
import com.pictures.domain.paging_source.PictureCachePagingSource

interface CacheDataSource {

    fun getPictureCachePagingSource(): PictureCachePagingSource

    fun getFavoritePictures(): List<PictureEntity>

    fun changeFavorites(id: Int, favorites: Boolean)

    fun getAllPicture(): List<PictureEntity>

    fun insert(pictureEntity: PictureEntity)

    fun delete(id: Int)

    fun deleteAll()
}