package com.example.footballmatchtracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {

    @Query("SELECT * FROM matches ORDER BY id DESC")
    fun getAllMatches(): Flow<List<FootballMatch>>

    @Query("SELECT * FROM matches WHERE id = :id")
    suspend fun getMatchById(id: Int): FootballMatch?

    @Insert
    suspend fun insertMatch(match: FootballMatch)

    @Update
    suspend fun updateMatch(match: FootballMatch)

    @Delete
    suspend fun deleteMatch(match: FootballMatch)
}