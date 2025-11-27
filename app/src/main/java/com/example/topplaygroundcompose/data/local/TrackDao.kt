package com.example.topplaygroundcompose.data.local

import androidx.room.*

@Dao
interface TrackDao {

    @Query("SELECT * FROM tracks")
    suspend fun getAll(): List<TrackEntity>

    @Query("SELECT * FROM tracks WHERE trackId = :id LIMIT 1")
    suspend fun getById(id: Long): TrackEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(track: TrackEntity)

    @Delete
    suspend fun delete(track: TrackEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM tracks WHERE trackId = :id)")
    suspend fun isFavorite(id: Long): Boolean
}
