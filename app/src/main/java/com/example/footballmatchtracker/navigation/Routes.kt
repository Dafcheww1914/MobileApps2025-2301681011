package com.example.footballmatchtracker.navigation

object Routes {
    const val MATCH_LIST = "match_list"
    const val ADD_MATCH = "add_match"
    const val EDIT_MATCH = "edit_match/{matchId}"
    const val MATCH_DETAILS = "match_details/{matchId}"
    const val ADD_EVENT = "add_event/{matchId}"

    fun editMatch(matchId: Int): String {
        return "edit_match/$matchId"
    }

    fun matchDetails(matchId: Int): String {
        return "match_details/$matchId"
    }

    fun addEvent(matchId: Int): String {
        return "add_event/$matchId"
    }
}