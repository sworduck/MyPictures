package com.pictures.data

import androidx.paging.PagingData
import androidx.paging.map
import com.pictures.data.data_sources.cache.CacheDataSource
import com.pictures.data.data_sources.cloud.RemoteDataSource
import com.pictures.domain.PictureData
import com.pictures.domain.repository.PictureRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PictureRepositoryImpl @Inject constructor(
    private val cacheDataSource: CacheDataSource,
    private val remoteDataSource: RemoteDataSource,
) : PictureRepository {

    override fun getAllFavoritePictureId(): Flow<List<Int>> {
        return cacheDataSource.getIdListFavoritePictures()
    }

    override fun getPhotos(): Flow<PagingData<PictureData>> {
        return remoteDataSource.getPhotos()

    /*.mapLatest { pagingData ->
            pagingData.map { picture ->
                if (picture.id.toInt() in idListFavoritePictures)
                    picture.copy(favorite = true) else picture
            }
        }*/
    }

    override fun savePicture(picture: PictureData): Result<Unit> {
        return kotlin.runCatching {
            cacheDataSource.insert(picture.toPictureEntity())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFavoritePictures(): Flow<PagingData<PictureData>> {
        return cacheDataSource.getFavoritePictures().mapLatest { pagingData ->
            pagingData.map { picture ->
                picture.toPictureData()
            }
        }
    }

    override fun insert(pictureEntity: PictureData) {
        cacheDataSource.insert(pictureEntity.toPictureEntity())
    }

    override fun delete(id: Int): Result<Unit> {
        return kotlin.runCatching {
            cacheDataSource.delete(id)
        }
    }

    override fun deleteAll() {
        cacheDataSource.deleteAll()
    }

}