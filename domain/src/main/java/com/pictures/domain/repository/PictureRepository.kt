package com.pictures.domain.repository

import androidx.paging.PagingData
import com.pictures.domain.PictureData
import kotlinx.coroutines.flow.Flow


interface PictureRepository {

    fun getAllFavoritePictureId(): Flow<List<Int>>

    fun getPhotos(): Flow<PagingData<PictureData>>

    fun savePicture(picture: PictureData) : Result<Unit>

    fun getFavoritePictures(): Flow<PagingData<PictureData>>

    fun insert(pictureEntity: PictureData)

    fun delete(id: Int): Result<Unit>

    fun deleteAll()
}