package com.example.topplaygroundcompose.di

import android.content.Context
import androidx.room.Room
import com.example.topplaygroundcompose.data.local.MusicDatabase
import com.example.topplaygroundcompose.data.local.TrackDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MusicDatabase =
        Room.databaseBuilder(
            context,
            MusicDatabase::class.java,
            "music_db"
        ).build()

    @Provides
    @Singleton
    fun provideTrackDao(db: MusicDatabase): TrackDao = db.trackDao()
}
