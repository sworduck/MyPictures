package com.pictures.data.data_sources.cache

import androidx.paging.PagingData
import com.pictures.data.database.entity.PictureEntity
import kotlinx.coroutines.flow.Flow

interface CacheDataSource {

    fun getIdListFavoritePictures(): List<Int>

    fun getFavoritePictures():  Flow<PagingData<PictureEntity>>

    fun insert(pictureEntity: PictureEntity)

    fun delete(id: Int)

    fun deleteAll()
}