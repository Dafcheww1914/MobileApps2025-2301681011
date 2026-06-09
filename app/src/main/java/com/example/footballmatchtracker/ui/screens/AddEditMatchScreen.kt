package com.example.footballmatchtracker.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.footballmatchtracker.R
import com.example.footballmatchtracker.data.FootballMatch
import com.example.footballmatchtracker.ui.theme.DarkBackground
import com.example.footballmatchtracker.ui.theme.FootballGreen
import com.example.footballmatchtracker.ui.theme.GrayText
import com.example.footballmatchtracker.ui.theme.WhiteText
import com.example.footballmatchtracker.viewmodel.MatchViewModel

private data class LogoOption(
    val name: String,
    val label: String,
    val drawableRes: Int
)

private val teamLogos = listOf(
    LogoOption("levski_logo", "Levski", R.drawable.levski_logo),
    LogoOption("botev_logo", "Botev", R.drawable.botev_logo),
    LogoOption("lokopld_logo", "Loko", R.drawable.loko_logo),
    LogoOption("litex_logo", "Litex", R.drawable.litex_logo),
    LogoOption("ludogoretc_logo", "Ludogorets", R.drawable.ludogorec_logo),
    LogoOption("more_logo", "Other", R.drawable.more_logo)
)

@Composable
fun AddMatchScreen(
    viewModel: MatchViewModel,
    matchToEdit: FootballMatch?,
    onBackClick: () -> Unit
) {
    var homeTeam by remember { mutableStateOf(matchToEdit?.homeTeam ?: "") }
    var awayTeam by remember { mutableStateOf(matchToEdit?.awayTeam ?: "") }
    var homeScore by remember { mutableStateOf(matchToEdit?.homeScore?.toString() ?: "") }
    var awayScore by remember { mutableStateOf(matchToEdit?.awayScore?.toString() ?: "") }
    var date by remember { mutableStateOf(matchToEdit?.date ?: "") }
    var stadium by remember { mutableStateOf(matchToEdit?.stadium ?: "") }
    var notes by remember { mutableStateOf(matchToEdit?.notes ?: "") }

    var homeLogo by remember { mutableStateOf(matchToEdit?.homeLogo ?: "more_logo") }
    var awayLogo by remember { mutableStateOf(matchToEdit?.awayLogo ?: "more_logo") }

    var errorMessage by remember { mutableStateOf("") }

    val isEditMode = matchToEdit != null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = if (isEditMode) "Edit Match" else "Add New Match",
            color = WhiteText,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = if (isEditMode) "Update match information" else "Enter match information",
            color = GrayText,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        MatchTextField(
            value = homeTeam,
            onValueChange = { homeTeam = it },
            label = "Home Team"
        )

        Text(
            text = "Home Team Logo",
            color = WhiteText,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp, bottom = 6.dp)
        )

        LogoSelector(
            selectedLogo = homeLogo,
            onLogoSelected = { homeLogo = it }
        )

        MatchTextField(
            value = awayTeam,
            onValueChange = { awayTeam = it },
            label = "Away Team"
        )

        Text(
            text = "Away Team Logo",
            color = WhiteText,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp, bottom = 6.dp)
        )

        LogoSelector(
            selectedLogo = awayLogo,
            onLogoSelected = { awayLogo = it }
        )

        MatchTextField(
            value = homeScore,
            onValueChange = { homeScore = it },
            label = "Home Score"
        )

        MatchTextField(
            value = awayScore,
            onValueChange = { awayScore = it },
            label = "Away Score"
        )

        MatchTextField(
            value = date,
            onValueChange = { date = it },
            label = "Date"
        )

        MatchTextField(
            value = stadium,
            onValueChange = { stadium = it },
            label = "Stadium"
        )

        MatchTextField(
            value = notes,
            onValueChange = { notes = it },
            label = "Notes"
        )

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val homeScoreInt = homeScore.toIntOrNull()
                val awayScoreInt = awayScore.toIntOrNull()

                if (
                    homeTeam.isBlank() ||
                    awayTeam.isBlank() ||
                    homeScoreInt == null ||
                    awayScoreInt == null ||
                    date.isBlank() ||
                    stadium.isBlank()
                ) {
                    errorMessage = "Please fill all required fields correctly."
                } else {
                    val match = FootballMatch(
                        id = matchToEdit?.id ?: 0,
                        homeTeam = homeTeam,
                        awayTeam = awayTeam,
                        homeScore = homeScoreInt,
                        awayScore = awayScoreInt,
                        date = date,
                        stadium = stadium,
                        notes = notes,
                        homeLogo = homeLogo,
                        awayLogo = awayLogo
                    )

                    if (isEditMode) {
                        viewModel.updateMatch(match)
                    } else {
                        viewModel.insertMatch(match)
                    }

                    onBackClick()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = FootballGreen,
                contentColor = Color.Black
            )
        ) {
            Text(
                text = if (isEditMode) "Update Match" else "Save Match",
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Back",
                color = FootballGreen
            )
        }
    }
}

@Composable
private fun LogoSelector(
    selectedLogo: String,
    onLogoSelected: (String) -> Unit
) {
    LazyRow {
        items(teamLogos) { logo ->
            val isSelected = selectedLogo == logo.name

            Column(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .clickable {
                        onLogoSelected(logo.name)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = logo.drawableRes),
                    contentDescription = logo.label,
                    modifier = Modifier
                        .size(58.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(
                            width = if (isSelected) 3.dp else 1.dp,
                            color = if (isSelected) FootballGreen else GrayText,
                            shape = CircleShape
                        )
                        .padding(6.dp)
                )

                Text(
                    text = logo.label,
                    color = if (isSelected) FootballGreen else GrayText,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun MatchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(label)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = FootballGreen,
            focusedLabelColor = FootballGreen,
            cursorColor = FootballGreen,
            focusedTextColor = WhiteText,
            unfocusedTextColor = WhiteText,
            unfocusedLabelColor = GrayText
        )
    )
}