package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val JptDarkColorScheme = darkColorScheme(
    primary = GoldPrimary,
    onPrimary = NavyDarkest,
    primaryContainer = GoldDark,
    onPrimaryContainer = GoldLight,
    secondary = GoldAccent,
    onSecondary = NavyDarkest,
    secondaryContainer = NavyElevated,
    onSecondaryContainer = GoldLight,
    tertiary = SlateLight,
    onTertiary = NavyDarkest,
    background = NavyDeep,
    onBackground = PureWhite,
    surface = NavySurface,
    onSurface = PureWhite,
    surfaceVariant = NavyElevated,
    onSurfaceVariant = SlateLight,
    outline = GoldBorder,
    outlineVariant = NavyBorder
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    // For Jamal Precision Tech, we enforce the signature Royal Navy & Metallic Brushed Gold aesthetic
    MaterialTheme(
        colorScheme = JptDarkColorScheme,
        typography = Typography,
        content = content
    )
}
