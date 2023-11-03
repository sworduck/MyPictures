package com.pictures.data.network.retrofit

import com.pictures.data.network.data.PictureCloud
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PictureApi {
    @GET("/v2/list")
    suspend fun getPictureList(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<List<PictureCloud>>

    @GET("/id/{photoId}/info")
    suspend fun fetchPhotoById(@Path("photoId") photoId: Long): PictureCloud
}