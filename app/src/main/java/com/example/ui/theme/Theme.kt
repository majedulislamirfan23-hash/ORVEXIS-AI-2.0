package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun OrvexisTheme(
    customPrimaryAccent: Color = ElectricViolet,
    customSecondaryAccent: Color = NeonCyan,
    content: @Composable () -> Unit
) {
    val darkColorScheme = darkColorScheme(
        primary = customPrimaryAccent,
        onPrimary = SoftWhite,
        primaryContainer = customPrimaryAccent.copy(alpha = 0.2f),
        onPrimaryContainer = SoftWhite,
        secondary = customSecondaryAccent,
        onSecondary = ObsidianBlack,
        secondaryContainer = customSecondaryAccent.copy(alpha = 0.15f),
        onSecondaryContainer = SoftWhite,
        tertiary = ChampagneGold,
        onTertiary = ObsidianBlack,
        background = ObsidianBlack,
        onBackground = SoftWhite,
        surface = DeepGraphite,
        onSurface = SoftWhite,
        surfaceVariant = SurfaceContainer,
        onSurfaceVariant = CoolGrey,
        outline = BorderGlow,
        outlineVariant = BorderSubtle,
        error = ErrorRose,
        onError = SoftWhite
    )

    MaterialTheme(
        colorScheme = darkColorScheme,
        typography = Typography,
        content = content
    )
}
