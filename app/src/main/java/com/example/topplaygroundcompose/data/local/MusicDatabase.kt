package com.example.topplaygroundcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TrackEntity::class],
    version = 1
)
abstract class MusicDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
}
