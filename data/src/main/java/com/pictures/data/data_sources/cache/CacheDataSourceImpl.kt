package com.pictures.data.data_sources.cache

import com.pictures.data.database.dao.PicturesDao
import com.pictures.data.database.entity.PictureEntity
import com.pictures.domain.paging_source.PictureCachePagingSource
import javax.inject.Inject

class CacheDataSourceImpl @Inject constructor(private val dao: PicturesDao) : CacheDataSource {

    override fun getPictureCachePagingSource(): PictureCachePagingSource {
        return PictureCachePagingSourceImpl(dao)
    }

    override fun getFavoritePictures(): List<PictureEntity> {
        return dao.getFavoritePictures()
    }

    override fun changeFavorites(id: Int, favorites: Boolean) {
        dao.update(dao.getPicture(id).copy(favorite = favorites))
    }

    override fun getAllPicture(): List<PictureEntity> {
        return dao.getAllPicture()
    }

    override fun insert(pictureEntity: PictureEntity) {
        dao.insert(pictureEntity)
    }

    override fun delete(id: Int) {
        dao.delete(id)
    }

    override fun deleteAll() {
        dao.deleteAll()
    }
}