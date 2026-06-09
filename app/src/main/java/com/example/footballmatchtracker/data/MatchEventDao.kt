package com.example.footballmatchtracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchEventDao {

    @Query("SELECT * FROM match_events WHERE matchId = :matchId ORDER BY minute ASC")
    fun getEventsForMatch(matchId: Int): Flow<List<MatchEvent>>

    @Insert
    suspend fun insertEvent(event: MatchEvent)

    @Delete
    suspend fun deleteEvent(event: MatchEvent)
}