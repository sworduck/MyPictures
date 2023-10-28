package com.pictures.data.di

import android.content.Context
import androidx.room.Room
import com.pictures.data.data_sources.cache.CacheDataSource
import com.pictures.data.data_sources.cache.CacheDataSourceImpl
import com.pictures.data.database.MyPictureDb
import com.pictures.data.database.dao.PicturesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
@InstallIn(SingletonComponent::class)
@Module
class DataBaseModule {

    @Provides
    @Singleton
    internal fun provideDataBase(@ApplicationContext context: Context): MyPictureDb {
        return Room.databaseBuilder(
            context,
            MyPictureDb::class.java,
            "testdb.db"
        )
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
}*/
