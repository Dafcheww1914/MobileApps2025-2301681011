package com.example.footballmatchtracker.repository

import com.example.footballmatchtracker.data.FootballMatch
import com.example.footballmatchtracker.data.MatchDao
import com.example.footballmatchtracker.data.MatchEvent
import com.example.footballmatchtracker.data.MatchEventDao
import kotlinx.coroutines.flow.Flow

class MatchRepository(
    private val matchDao: MatchDao,
    private val eventDao: MatchEventDao
) {
    val allMatches: Flow<List<FootballMatch>> = matchDao.getAllMatches()

    suspend fun getMatchById(id: Int): FootballMatch? {
        return matchDao.getMatchById(id)
    }

    suspend fun insertMatch(match: FootballMatch) {
        matchDao.insertMatch(match)
    }

    suspend fun updateMatch(match: FootballMatch) {
        matchDao.updateMatch(match)
    }

    suspend fun deleteMatch(match: FootballMatch) {
        matchDao.deleteMatch(match)
    }

    fun getEventsForMatch(matchId: Int): Flow<List<MatchEvent>> {
        return eventDao.getEventsForMatch(matchId)
    }

    suspend fun insertEvent(event: MatchEvent) {
        eventDao.insertEvent(event)
    }

    suspend fun deleteEvent(event: MatchEvent) {
        eventDao.deleteEvent(event)
    }
}