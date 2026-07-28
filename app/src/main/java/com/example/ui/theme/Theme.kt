package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val iOSPurpleColorScheme = lightColorScheme(
    primary = iOSPurplePrimary,
    onPrimary = Color.White,
    primaryContainer = iOSPurpleContainer,
    onPrimaryContainer = iOSPurpleDark,
    secondary = iOSPurpleSecondary,
    onSecondary = Color.White,
    background = iOSWhiteBackground,
    onBackground = TextDark,
    surface = iOSCardSurface,
    onSurface = TextDark,
    surfaceVariant = iOSPurpleLight,
    onSurfaceVariant = iOSPurpleText
)

@Composable
fun AuraAITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = iOSPurpleColorScheme,
        typography = Typography,
        content = content
    )
}

