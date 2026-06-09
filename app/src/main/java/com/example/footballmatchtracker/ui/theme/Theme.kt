package com.example.footballmatchtracker.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkFootballColorScheme = darkColorScheme(
    primary = FootballGreen,
    secondary = LightGreen,
    background = DarkBackground,
    surface = CardBackground,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = WhiteText,
    onSurface = WhiteText
)

@Composable
fun FootballMatchTrackerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkFootballColorScheme,
        content = content
    )
}