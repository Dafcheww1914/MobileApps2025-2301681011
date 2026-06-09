package com.example.footballmatchtracker

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.footballmatchtracker.data.AppDatabase
import com.example.footballmatchtracker.data.FootballMatch
import com.example.footballmatchtracker.repository.MatchRepository
import com.example.footballmatchtracker.ui.screens.AddMatchScreen
import com.example.footballmatchtracker.ui.screens.FootballHomeScreen
import com.example.footballmatchtracker.ui.theme.FootballMatchTrackerTheme
import com.example.footballmatchtracker.viewmodel.MatchViewModel
import com.example.footballmatchtracker.viewmodel.MatchViewModelFactory

class MainActivity : ComponentActivity() {

    private var matchToEdit by mutableStateOf<FootballMatch?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)

        val repository = MatchRepository(
            matchDao = database.matchDao(),
            eventDao = database.matchEventDao()
        )

        val factory = MatchViewModelFactory(repository)

        setContent {
            FootballMatchTrackerTheme {
                val navController = rememberNavController()
                val matchViewModel: MatchViewModel = viewModel(factory = factory)

                NavHost(
                    navController = navController,
                    startDestination = "match_list"
                ) {
                    composable("match_list") {
                        FootballHomeScreen(
                            viewModel = matchViewModel,
                            onAddClick = {
                                matchToEdit = null
                                navController.navigate("add_match")
                            },
                            onEditClick = { match ->
                                matchToEdit = match
                                navController.navigate("add_match")
                            },
                            onShareClick = { match ->
                                shareMatch(match)
                            }
                        )
                    }

                    composable("add_match") {
                        AddMatchScreen(
                            viewModel = matchViewModel,
                            matchToEdit = matchToEdit,
                            onBackClick = {
                                matchToEdit = null
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }

    private fun shareMatch(match: FootballMatch) {
        val shareText = """
            ${match.homeTeam} ${match.homeScore} - ${match.awayScore} ${match.awayTeam}
            Date: ${match.date}
            Stadium: ${match.stadium}
            Notes: ${match.notes}
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }

        startActivity(
            Intent.createChooser(intent, "Share match")
        )
    }
}