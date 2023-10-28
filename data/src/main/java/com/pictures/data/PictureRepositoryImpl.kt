package com.pictures.data

import androidx.paging.PagingData
import com.pictures.data.data_sources.cache.CacheDataSource
import com.pictures.data.data_sources.cloud.RemoteDataSource
import com.pictures.data.database.entity.PictureEntity
import com.pictures.domain.PictureData
import com.pictures.domain.paging_source.PictureCachePagingSource
import com.pictures.domain.paging_source.PictureCloudPagingSource
import com.pictures.domain.repository.PictureRepository
import com.pictures.domain.repository.PictureRepository.Companion.pictureListIdFromCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PictureRepositoryImpl @Inject constructor(
    private val cacheDataSource: CacheDataSource,
    private val remoteDataSource: RemoteDataSource
) : PictureRepository {

    override fun getPictureCachePagingSource(): PictureCachePagingSource {
        return cacheDataSource.getPictureCachePagingSource()
    }

    override fun getPictureCloudPagingSource(): PictureCloudPagingSource {
        CoroutineScope(Dispatchers.IO).launch {
            pictureListIdFromCache.addAll(cacheDataSource.getAllPicture()
                .map { it.id.toInt() })
        }
        return remoteDataSource.getPictureCloudPagingSource()
    }

    override fun savePicture(picture: PictureData) {
        cacheDataSource.insert(picture.toPictureEntity())
    }

    override fun getFavoritePictures(): List<PictureData> {
        return cacheDataSource.getFavoritePictures().map {
            it.toPictureData()
        }
    }

    override fun changeFavorites(id: Int, favorites: Boolean) {
        cacheDataSource.changeFavorites(id,favorites)
    }

    override fun getAllPicture(): List<PictureData> {
        return cacheDataSource.getAllPicture().map {
            it.toPictureData()
        }
    }

    override fun insert(pictureEntity: PictureData) {
        cacheDataSource.insert(pictureEntity.toPictureEntity())
    }

    override fun delete(id: Int) {
        cacheDataSource.delete(id)
    }

    override fun deleteAll() {
        cacheDataSource.deleteAll()
    }

    fun savePicture(picture: PictureEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            cacheDataSource.insert(picture)
            pictureListIdFromCache.add(picture.id.toInt())
        }
    }

    fun removePicture(picture: PictureEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            cacheDataSource.delete(picture.id.toInt())
            pictureListIdFromCache.remove(picture.id.toInt())
        }
    }

}