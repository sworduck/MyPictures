package com.pictures.data

import com.pictures.data.database.entity.PictureEntity
import com.pictures.data.network.data.PictureCloud
import com.pictures.domain.PictureData
import junit.framework.Assert.assertEquals
import org.junit.Test

class MapperTest {

    val cloudPicture = PictureCloud(
        id = "1",
        author = "author",
        height = 1,
        width = 1,
        url = "url",
        downloadUrl = "downloadUrl"
    )

    val cachePicture = PictureEntity(
        id = 1,
        author = "author",
        height = 1,
        width = 1,
        url = "url",
        downloadUrl = "downloadUrl",
        favorite = false
    )

    val defaultPicture = PictureData(
        id = 1,
        author = "author",
        height = 1,
        width = 1,
        url = "url",
        downloadUrl = "downloadUrl",
        favorite = false
    )

    @Test
    fun testAllMap(){
        assertEquals(defaultPicture, cloudPicture.toPictureData())
        assertEquals(defaultPicture, cachePicture.toPictureData())
        assertEquals(cachePicture, defaultPicture.toPictureEntity())
    }
}