package com.pictures.data.data_sources.cache

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pictures.data.database.dao.PicturesDao
import com.pictures.data.database.entity.PictureEntity
import com.pictures.data.network.NetworkConstant
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CacheDataSourceImpl @Inject constructor(private val dao: PicturesDao) : CacheDataSource {

    override fun getIdListFavoritePictures(): Flow<List<Int>> {
        return dao.getIdListFavoritePictures()
    }

    override fun getFavoritePictures(): Flow<PagingData<PictureEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = NetworkConstant.NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { dao.getFavoritePictures() }
        ).flow
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