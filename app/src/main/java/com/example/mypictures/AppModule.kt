package com.example.mypictures

import android.content.Context
import androidx.room.Room
import com.pictures.data.PictureRepositoryImpl
import com.pictures.data.data_sources.cache.CacheDataSource
import com.pictures.data.data_sources.cache.CacheDataSourceImpl
import com.pictures.data.data_sources.cloud.RemoteDataSource
import com.pictures.data.data_sources.cloud.RemoteDataSourceImpl
import com.pictures.data.database.MyPictureDb
import com.pictures.data.database.dao.PicturesDao
import com.pictures.data.network.NetworkConstant
import com.pictures.data.network.retrofit.PictureApi
import com.pictures.domain.repository.PictureRepository
import com.pictures.domain.usecases.DeleteFavoritePictureUseCase
import com.pictures.domain.usecases.GetFavoritePictureUseCase
import com.pictures.domain.usecases.GetPagePictureUseCase
import com.pictures.domain.usecases.SaveFavoritePictureUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    internal fun provideDataBase(@ApplicationContext context: Context): MyPictureDb {
        return Room.databaseBuilder(
            context,
            MyPictureDb::class.java,
            "testdb.db"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    internal fun providePicturesDao(db: MyPictureDb): PicturesDao {
        return db.picturesDao()
    }

    @Provides
    @Singleton
    fun provideCacheDataSource(dao: PicturesDao): CacheDataSource {
        return CacheDataSourceImpl(dao)
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit =
        Retrofit.Builder()
            .baseUrl(NetworkConstant.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            //.addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideGetPagePictureUseCase(repository: PictureRepository, coroutineDispatcher: CoroutineDispatcher): GetPagePictureUseCase =
        GetPagePictureUseCase(repository,coroutineDispatcher)

    @Provides
    @Singleton
    fun provideSaveFavoritePictureUseCase(repository: PictureRepository, coroutineDispatcher: CoroutineDispatcher): SaveFavoritePictureUseCase =
        SaveFavoritePictureUseCase(repository,coroutineDispatcher)

    @Provides
    @Singleton
    fun provideDeleteFavoritePictureUseCase(repository: PictureRepository, coroutineDispatcher: CoroutineDispatcher): DeleteFavoritePictureUseCase =
        DeleteFavoritePictureUseCase(repository,coroutineDispatcher)

    @Provides
    @Singleton
    fun provideGetFavoritePicturesUseCase(repository: PictureRepository, coroutineDispatcher: CoroutineDispatcher): GetFavoritePictureUseCase =
        GetFavoritePictureUseCase(repository,coroutineDispatcher)

    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): PictureApi {
        return retrofit.create(PictureApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCloudDataSource(apiService: PictureApi): RemoteDataSource {
        return RemoteDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun providePictureRepository(
        cacheDataSource: CacheDataSource,
        remoteDataSource: RemoteDataSource,
    ): PictureRepository {
        return PictureRepositoryImpl(
            cacheDataSource = cacheDataSource,
            remoteDataSource = remoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): Converter.Factory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
}