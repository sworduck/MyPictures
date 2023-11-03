package com.pictures.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pictures.data.data_sources.cache.CacheDataSourceImpl
import com.pictures.data.data_sources.cloud.RemoteDataSourceImpl
import com.pictures.data.database.MyPictureDb
import com.pictures.data.database.dao.PicturesDao
import com.pictures.data.database.entity.PictureEntity
import com.pictures.data.paging_source_test.cloud.FakePictureApi
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryTest {

    lateinit var db: MyPictureDb
    lateinit var dao: PicturesDao
    private fun defaultPictureEntity(id:Int) = PictureEntity(id.toLong(), "author", 1, 1, "url", true, "downloadUrl")


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
    fun saveGetTest(){
        val repositoryImpl = PictureRepositoryImpl(
            CacheDataSourceImpl(dao),
            RemoteDataSourceImpl(FakePictureApi())
        )

        repositoryImpl.insert(defaultPictureEntity(0).toPictureData())
        CoroutineScope(Dispatchers.IO).launch {
            repositoryImpl.getAllFavoritePictureId().collectLatest {
                assertEquals(it, listOf(0))
            }
        }
    }
}