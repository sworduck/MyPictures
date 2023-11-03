package com.pictures.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pictures.data.database.MyPictureDb
import com.pictures.data.database.dao.PicturesDao
import com.pictures.data.database.entity.PictureEntity
import com.pictures.data.network.NetworkConstant
import com.pictures.data.network.data.PictureCloud
import com.pictures.data.network.retrofit.PictureApi
import com.pictures.data.network.retrofit.provideRetrofit
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response
import retrofit2.Retrofit

@RunWith(AndroidJUnit4::class)
class RetrofitRoomTest {

    private lateinit var db: MyPictureDb
    private lateinit var dao: PicturesDao
    private val testedId = 5L

    @Test
    fun testGetPeopleResponse() {
        val service = provideRetrofit().create(PictureApi::class.java)
        var response: Response<List<PictureCloud>>?
        runBlocking {
            response = service.getPictureList(1,10)
        }
        val errorBody = response?.errorBody()
        assert(errorBody == null)
        val responseWrapper = response?.body()
        assert(responseWrapper != null)
        assert(response?.code() == 200)
    }

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
        assert(dao.getAllPicture().firstOrNull{it.id == testedId} != null)
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