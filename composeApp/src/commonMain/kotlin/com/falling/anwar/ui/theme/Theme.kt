package com.falling.anwar.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = BrandBlue,
    secondary = CareTeal,
    background = HealthcareBackground,
    surface = HealthcareSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = HealthcareTextPrimary,
    onSurface = HealthcareTextPrimary,
    error = AlertRed,
    outline = HealthcareDivider,
    surfaceVariant = HealthcareWhite,
    onSurfaceVariant = HealthcareTextSecondary
)

@Composable
fun FallingTheme(
    content: @Composable () -> Unit
) {
    // Healthcare grade apps usually prefer a consistent clinical look (mostly white)
    // Forced light mode as per requirements
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = FallingTypography,
        content = content
    )
}
