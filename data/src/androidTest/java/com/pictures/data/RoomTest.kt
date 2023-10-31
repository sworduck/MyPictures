package com.pictures.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pictures.data.database.MyPictureDb
import com.pictures.data.database.dao.PicturesDao
import com.pictures.data.database.entity.PictureEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var db: MyPictureDb
    private lateinit var dao: PicturesDao
    private val testedId = 5L

    private val tempPicture1 = PictureEntity(
        testedId,
        "author",
        1,
        1,
        "url",
        false,
        "downloadUrl"
    )

    private val tempPicture2 = PictureEntity(
        testedId+1,
        "author",
        1,
        1,
        "url",
        false,
        "downloadUrl"
    )

    @Before
    fun createDb() {
        db = Room.databaseBuilder(
            getApplicationContext(),
            MyPictureDb::class.java,
            "testdb.db"
        )
            .fallbackToDestructiveMigration()
            .build()
        dao = db.picturesDao()
        dao.deleteAll()
    }

    @Test
    fun testInsert() {
        dao.insert(tempPicture1)
        dao.insert(tempPicture2)
        assert(dao.getFavoritePictures().firstOrNull{it.id == testedId} != null)
    }

    @Test
    fun testGetAll(){
        dao.deleteAll()
        dao.insert(tempPicture1)
        dao.insert(tempPicture2)
        assert(
            dao.getAllPicture() == listOf(tempPicture1, tempPicture2)
        )
    }

    @Test
    fun testDelete() {
        dao.insert(tempPicture1)
        dao.delete(tempPicture1.id.toInt())
        assert(dao.getAllPicture().contains(tempPicture1).not())
    }

    @Test
    fun testDeleteAll() {
        dao.insert(tempPicture1)
        dao.insert(tempPicture2)
        dao.deleteAll()
        assert(dao.getAllPicture().isEmpty())
    }

    @After
    fun closeDb() {
        db.close()
    }
}