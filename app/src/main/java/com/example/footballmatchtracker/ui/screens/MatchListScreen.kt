package com.example.footballmatchtracker.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.footballmatchtracker.R
import com.example.footballmatchtracker.data.FootballMatch
import com.example.footballmatchtracker.ui.theme.CardBackground
import com.example.footballmatchtracker.ui.theme.DarkBackground
import com.example.footballmatchtracker.ui.theme.FootballGreen
import com.example.footballmatchtracker.ui.theme.GrayText
import com.example.footballmatchtracker.ui.theme.WhiteText
import com.example.footballmatchtracker.viewmodel.MatchViewModel

@Composable
fun FootballHomeScreen(
    viewModel: MatchViewModel,
    onAddClick: () -> Unit,
    onEditClick: (FootballMatch) -> Unit,
    onShareClick: (FootballMatch) -> Unit
) {
    val matches: List<FootballMatch> by viewModel.allMatches.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            HeaderSection(matches = matches)

            Spacer(modifier = Modifier.height(18.dp))

            if (matches.isEmpty()) {
                EmptyState()
            } else {
                LazyColumn {
                    items(matches) { match ->
                        MatchCard(
                            match = match,
                            onEditClick = {
                                onEditClick(match)
                            },
                            onShareClick = {
                                onShareClick(match)
                            },
                            onDeleteClick = {
                                viewModel.deleteMatch(match)
                            }
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = onAddClick,
            containerColor = FootballGreen,
            contentColor = Color.Black,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Match"
            )
        }
    }
}

@Composable
private fun HeaderSection(
    matches: List<FootballMatch>
) {
    val totalGoals = matches.sumOf { it.homeScore + it.awayScore }
    val avgGoals = if (matches.isNotEmpty()) {
        totalGoals.toDouble() / matches.size
    } else {
        0.0
    }

    Column {
        Text(
            text = "Football Match Tracker",
            color = WhiteText,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold
        )

        Text(
            text = "Track scores, stadiums and football events",
            color = GrayText,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatBox(
                title = "Matches",
                value = matches.size.toString()
            )

            StatBox(
                title = "Goals",
                value = totalGoals.toString()
            )

            StatBox(
                title = "Avg",
                value = "%.1f".format(avgGoals)
            )
        }
    }
}

@Composable
private fun StatBox(
    title: String,
    value: String
) {
    Column(
        modifier = Modifier
            .background(
                color = CardBackground,
                shape = RoundedCornerShape(18.dp)
            )
            .border(
                width = 1.dp,
                color = FootballGreen.copy(alpha = 0.35f),
                shape = RoundedCornerShape(18.dp)
            )
            .padding(horizontal = 18.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            color = FootballGreen,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold
        )

        Text(
            text = title,
            color = GrayText,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = CardBackground,
                shape = RoundedCornerShape(22.dp)
            )
            .border(
                width = 1.dp,
                color = FootballGreen.copy(alpha = 0.3f),
                shape = RoundedCornerShape(22.dp)
            )
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "⚽",
            style = MaterialTheme.typography.displaySmall
        )

        Text(
            text = "No matches yet",
            color = WhiteText,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Tap + to add your first football match.",
            color = GrayText,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}

@Composable
private fun MatchCard(
    match: FootballMatch,
    onEditClick: () -> Unit,
    onShareClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 9.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        ),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeamColumn(
                    teamName = match.homeTeam,
                    logoName = match.homeLogo,
                    modifier = Modifier.weight(1f)
                )

                ScoreBox(
                    homeScore = match.homeScore,
                    awayScore = match.awayScore
                )

                TeamColumn(
                    teamName = match.awayTeam,
                    logoName = match.awayLogo,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            ResultBadge(match = match)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "📅 ${match.date}",
                color = GrayText,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "🏟 ${match.stadium}",
                color = GrayText,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )

            if (match.notes.isNotBlank()) {
                Text(
                    text = "📝 ${match.notes}",
                    color = GrayText,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                ActionButton(
                    icon = Icons.Default.Edit,
                    contentDescription = "Edit Match",
                    onClick = onEditClick
                )

                Spacer(modifier = Modifier.width(6.dp))

                ActionButton(
                    icon = Icons.Default.Share,
                    contentDescription = "Share Match",
                    onClick = onShareClick
                )

                Spacer(modifier = Modifier.width(6.dp))

                ActionButton(
                    icon = Icons.Default.Delete,
                    contentDescription = "Delete Match",
                    onClick = onDeleteClick
                )
            }
        }
    }
}

@Composable
private fun TeamColumn(
    teamName: String,
    logoName: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = getLogoResource(logoName)),
            contentDescription = teamName,
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = FootballGreen.copy(alpha = 0.5f),
                    shape = CircleShape
                )
                .padding(6.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = teamName,
            color = WhiteText,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

private fun getLogoResource(logoName: String): Int {
    return when (logoName) {
        "levski_logo" -> R.drawable.levski_logo
        "botev_logo" -> R.drawable.botev_logo
        "lokopld_logo" -> R.drawable.loko_logo
        "litex_logo" -> R.drawable.litex_logo
        "ludogoretc_logo" -> R.drawable.ludogorec_logo
        "more_logo" -> R.drawable.more_logo
        else -> R.drawable.more_logo
    }
}

@Composable
private fun ScoreBox(
    homeScore: Int,
    awayScore: Int
) {
    Box(
        modifier = Modifier
            .background(
                color = FootballGreen,
                shape = RoundedCornerShape(18.dp)
            )
            .padding(horizontal = 18.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$homeScore - $awayScore",
            color = Color.Black,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
private fun ResultBadge(
    match: FootballMatch
) {
    val badgeText = when {
        match.homeScore > match.awayScore -> "Home win"
        match.homeScore < match.awayScore -> "Away win"
        else -> "Draw"
    }

    Box(
        modifier = Modifier
            .background(
                color = FootballGreen.copy(alpha = 0.12f),
                shape = RoundedCornerShape(50.dp)
            )
            .border(
                width = 1.dp,
                color = FootballGreen.copy(alpha = 0.45f),
                shape = RoundedCornerShape(50.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = "⚽ $badgeText",
            color = FootballGreen,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ActionButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(42.dp)
            .background(
                color = FootballGreen.copy(alpha = 0.12f),
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = FootballGreen
        )
    }
}