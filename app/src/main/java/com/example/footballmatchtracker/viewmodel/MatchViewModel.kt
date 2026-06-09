package com.example.footballmatchtracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.footballmatchtracker.data.FootballMatch
import com.example.footballmatchtracker.data.MatchEvent
import com.example.footballmatchtracker.repository.MatchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MatchViewModel(
    private val repository: MatchRepository
) : ViewModel() {

    val allMatches: StateFlow<List<FootballMatch>> =
        repository.allMatches.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun getEventsForMatch(matchId: Int): Flow<List<MatchEvent>> {
        return repository.getEventsForMatch(matchId)
    }

    suspend fun getMatchById(id: Int): FootballMatch? {
        return repository.getMatchById(id)
    }

    fun insertMatch(match: FootballMatch) {
        viewModelScope.launch {
            repository.insertMatch(match)
        }
    }

    fun updateMatch(match: FootballMatch) {
        viewModelScope.launch {
            repository.updateMatch(match)
        }
    }

    fun deleteMatch(match: FootballMatch) {
        viewModelScope.launch {
            repository.deleteMatch(match)
        }
    }

    fun insertEvent(event: MatchEvent) {
        viewModelScope.launch {
            repository.insertEvent(event)
        }
    }

    fun deleteEvent(event: MatchEvent) {
        viewModelScope.launch {
            repository.deleteEvent(event)
        }
    }
}

class MatchViewModelFactory(
    private val repository: MatchRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MatchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MatchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}