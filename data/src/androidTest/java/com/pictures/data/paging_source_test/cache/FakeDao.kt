package com.pictures.data.paging_source_test.cache

import androidx.paging.PagingSource
import com.pictures.data.database.dao.PicturesDao
import com.pictures.data.database.entity.PictureEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow

class FakeDao : PicturesDao {

    private val pictureList = mutableListOf<PictureEntity>()
    private val idList = mutableListOf<Int>()

    private val defaultPictureEntity = PictureEntity(1, "author", 1, 1, "url", false, "downloadUrl")


    override fun delete(id: Int) {
        pictureList.removeIf { it.id.toInt() == id }
    }

    override fun deleteAll() {
        pictureList.clear()
    }

    override fun getAllPicture(): List<PictureEntity> {
        return pictureList
    }

    override fun getAllPicturePaging(): PagingSource<Int, PictureEntity> {
        TODO("Not yet implemented")
    }

    override fun getFavoritePictures(): PagingSource<Int, PictureEntity> {
        TODO("Not yet implemented")
    }

    override fun getPictureById(id: Int): PictureEntity {
        return pictureList.firstOrNull { it.id.toInt() == id } ?: return defaultPictureEntity
    }

    override fun getIdListFavoritePictures(): Flow<List<Int>> {
        return flow { idList }
    }

    override fun update(pictureEntity: PictureEntity) {
        defaultPictureEntity
    }

    override fun insert(vararg pictureEntity: PictureEntity) {
        pictureEntity.forEach {
            pictureList.add(it)
            idList.add(it.id.toInt())
        }
    }

    override suspend fun insertAll(repos: List<PictureEntity>) {
        pictureList.addAll(repos)
    }


}