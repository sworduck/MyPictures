package com.pictures.domain

import androidx.paging.PagingData
import com.pictures.domain.repository.PictureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow

class FakePictureRepository : PictureRepository {

    private val pictures: MutableList<PictureData> = mutableListOf()
    private val picturesId: MutableList<Int> = mutableListOf()

    override fun getAllFavoritePictureId(): Flow<List<Int>> {
        return flow { picturesId }
    }

    override fun getPhotos(): Flow<PagingData<PictureData>> {
        return flow {
            PagingData.from(pictures)
        }
    }

    override fun savePicture(picture: PictureData): Result<Unit> {
        pictures.add(picture)
        picturesId.add(picture.id.toInt())
        return Result.success(Unit)
    }

    override fun getFavoritePictures(): Flow<PagingData<PictureData>> {
        TODO("Not yet implemented")
    }

    override fun insert(pictureData: PictureData) {
        pictures.add(pictureData)
        picturesId.add(pictureData.id.toInt())
    }

    override fun delete(id: Int): Result<Unit> {
        pictures.filter { it.id.toInt() != id }
        picturesId.filter { it != id }
        return Result.success(Unit)
    }

    override fun deleteAll() {
        pictures.clear()
        picturesId.clear()
    }
}