package com.pictures.data.paging_source_test.cloud

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.pictures.data.data_sources.cloud.PictureCloudPagingSourceImpl
import com.pictures.data.network.NetworkConstant
import com.pictures.data.network.data.PictureCloud
import kotlinx.coroutines.flow.collectLatest
import androidx.paging.PagingSource.LoadResult
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PagingCloudDataSourceTest {

    private fun defaultPicture(id: String) =
        PictureCloud(
            id = id,
            author = "author",
            height = 1,
            width = 1,
            url = "url",
            downloadUrl = "downloadUrl"
        )

    private val mockPosts = listOf(
        defaultPicture("1"),
        defaultPicture("2"),
        defaultPicture("3")
    )
    private val fakeApi = FakePictureApi().apply {
        mockPosts.forEach { post -> addPost(post) }
    }

    @Test
    fun loadReturnsPage() {
        val pagingSource = PictureCloudPagingSourceImpl(
            fakeApi
        )

        val pager = Pager(
            config = PagingConfig(
                pageSize = NetworkConstant.NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                pagingSource
            }
        ).flow

        CoroutineScope(Dispatchers.IO).launch {
            pager.collectLatest {
                val result: LoadResult.Page<Int, PictureCloud> =
                    it as LoadResult.Page<Int, PictureCloud>

                // Write assertions against the loaded data
                assertEquals(result.data, mockPosts)
            }
        }
    }
}