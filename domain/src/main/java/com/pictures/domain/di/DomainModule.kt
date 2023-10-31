package com.pictures.domain.di

import com.pictures.domain.repository.PictureRepository
import com.pictures.domain.usecases.DeleteFavoritePictureUseCase
import com.pictures.domain.usecases.GetFavoritePictureUseCase
import com.pictures.domain.usecases.GetPagePictureUseCase
import com.pictures.domain.usecases.SaveFavoritePictureUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DomainModule {

    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

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
}