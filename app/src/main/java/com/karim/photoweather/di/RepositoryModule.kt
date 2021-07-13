package com.karim.photoweather.di

import com.karim.photoweather.network.WeatherClient
import com.karim.photoweather.persistence.PhotosDao
import com.karim.photoweather.repository.PhotosRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun providePhotosRepository(
        weatherClient: WeatherClient,
        photosDao: PhotosDao
    ): PhotosRepository{
        return PhotosRepository(weatherClient, photosDao)
    }
}