package com.pictures.data.network.retrofit

import com.pictures.data.network.data.Picture
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PictureApi {
    @GET("/v2/list")
    suspend fun getPictureList(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): List<Picture>

    @GET("/id/{photoId}/info")
    suspend fun fetchPhotoById(@Path("photoId") photoId: Long): Picture
}