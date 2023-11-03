package com.pictures.data.paging_source_test.cloud

import com.pictures.data.network.data.PictureCloud
import com.pictures.data.network.retrofit.PictureApi
import retrofit2.Response

class FakePictureApi: PictureApi {
    private val mockPosts = mutableListOf<PictureCloud>()
    fun addPost(post: PictureCloud){
        mockPosts.add(post)
    }

    override suspend fun getPictureList(page: Int, limit: Int): Response<List<PictureCloud>> {
        return Response.success(mockPosts)
    }

    override suspend fun fetchPhotoById(photoId: Long): PictureCloud {
        return mockPosts.first { it.id.toLong() == photoId }
    }
}