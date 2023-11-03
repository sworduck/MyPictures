package com.pictures.domain

import android.util.Log
import androidx.paging.map
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pictures.domain.usecases.DeleteFavoritePictureUseCase
import com.pictures.domain.usecases.GetFavoritePictureUseCase
import com.pictures.domain.usecases.SaveFavoritePictureUseCase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UseCasesTests {

    private val fakePictureRepository = FakePictureRepository()
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    private fun defaultPicture(id:Int) = PictureData(
        id = id.toLong(), author =  "author", height = 1, width =  1, url =  "url", favorite =  true, downloadUrl =  "downloadUrl")


    @Test
    fun favoriteUseCasesTest(){
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                SaveFavoritePictureUseCase(
                    fakePictureRepository,
                    dispatcher
                ).invoke(pictureData = defaultPicture(0))
            }
        }
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                GetFavoritePictureUseCase(
                    fakePictureRepository,
                    dispatcher
                ).invoke().collect {
                    assertEquals(it, defaultPicture(0))
                }
            }
        }
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                DeleteFavoritePictureUseCase(
                    fakePictureRepository,
                    dispatcher
                ).invoke(pictureData = defaultPicture(0))
            }
        }
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                GetFavoritePictureUseCase(
                    fakePictureRepository,
                    dispatcher
                ).invoke().collect {
                    assertEquals(it , listOf<PictureData>())
                }
            }
        }
    }
}