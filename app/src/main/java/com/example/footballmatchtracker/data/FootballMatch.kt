package com.example.footballmatchtracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches")
data class FootballMatch(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val homeTeam: String,
    val awayTeam: String,
    val homeScore: Int,
    val awayScore: Int,
    val date: String,
    val stadium: String,
    val notes: String,
    val homeLogo: String = "more_logo",
    val awayLogo: String = "more_logo"
)