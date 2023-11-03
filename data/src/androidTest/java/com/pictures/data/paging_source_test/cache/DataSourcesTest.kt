package com.pictures.data.paging_source_test.cache

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pictures.data.data_sources.cache.CacheDataSourceImpl
import com.pictures.data.data_sources.cloud.PictureCloudPagingSourceImpl
import com.pictures.data.database.MyPictureDb
import com.pictures.data.database.dao.PicturesDao
import com.pictures.data.database.entity.PictureEntity
import com.pictures.data.network.NetworkConstant
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DataSourcesTest {

    private lateinit var db: MyPictureDb
    private lateinit var dao: PicturesDao
    private fun defaultPictureEntity(id:Int) = PictureEntity(id.toLong(), "author", 1, 1, "url", true, "downloadUrl")
    private val mockPosts = listOf(
        defaultPictureEntity(1),
        defaultPictureEntity(1),
        defaultPictureEntity(1)
    )

    @Before
    fun createDb() {
        db = Room.databaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MyPictureDb::class.java,
            "testdb.db"
        )
            .fallbackToDestructiveMigration()
            .build()
        dao = db.picturesDao()
        dao.deleteAll()
    }

    @Test
    fun cacheDataSourceTest() {
        val cacheDataSourceImpl = CacheDataSourceImpl(dao)
        cacheDataSourceImpl.insert(defaultPictureEntity(0))
        CoroutineScope(Dispatchers.IO).launch{
            cacheDataSourceImpl.getIdListFavoritePictures().collectLatest {
                assertEquals(it, listOf(0))
            }
        }
    }

    @Test
    fun loadPagingTest(){

        db = Room.databaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MyPictureDb::class.java,
            "testdb.db"
        )
            .fallbackToDestructiveMigration()
            .build()
        dao = db.picturesDao()
        dao.deleteAll()

        runBlocking {
            dao.insertAll(mockPosts)
        }
        val pager = Pager(
            config = PagingConfig(
                pageSize = NetworkConstant.NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                dao.getFavoritePictures()
            }
        ).flow

        CoroutineScope(Dispatchers.IO).launch {
            pager.collectLatest {
                val result: PagingSource.LoadResult.Page<Int, PictureEntity> =
                    it as PagingSource.LoadResult.Page<Int, PictureEntity>
                assertEquals(result.data, mockPosts)
            }
        }
    }
}