package com.pictures.data

import com.pictures.data.network.NetworkConstant
import com.pictures.data.network.retrofit.PictureApi
import com.pictures.data.network.retrofit.provideRetrofit
import org.junit.Test
import retrofit2.Retrofit

class RetrofitTest {
    @Test
    fun testRetrofitInstance() {
        //Get an instance of Retrofit
        val instance: Retrofit = provideRetrofit()
        //Assert that, Retrofit's base url matches to our BASE_URL
        assert(instance.baseUrl().url().toString() == NetworkConstant.BASE_URL)
    }
    @Test
    fun testGetPeopleResponse() {
        val service = provideRetrofit().create(PictureApi::class.java)
        val response = service.getPictureList().execute()
        val errorBody = response.errorBody()
        assert(errorBody == null)
        val responseWrapper = response.body()
        assert(responseWrapper != null)
        assert(response.code() == 200)
    }
}