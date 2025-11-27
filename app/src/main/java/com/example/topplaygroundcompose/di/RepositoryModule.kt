package com.example.topplaygroundcompose.di

import com.example.topplaygroundcompose.data.local.TrackDao
import com.example.topplaygroundcompose.data.remote.MusicApiService
import com.example.topplaygroundcompose.data.repository.MusicRepositoryImpl
import com.example.topplaygroundcompose.domain.repository.MusicRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMusicRepository(
        api: MusicApiService,
        dao: TrackDao
    ): MusicRepository = MusicRepositoryImpl(api, dao)
}
