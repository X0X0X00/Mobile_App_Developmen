package com.zzh133.country.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80

    primary = BluePrimaryDark,
    secondary = GoldSecondaryDark,
    tertiary = GrayTertiaryDark
)

private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40

    primary = BluePrimary,
    secondary = GoldSecondary,
    tertiary = GrayTertiary
)

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */




@Composable
fun MyApplicationTheme(
    selectedTheme: String = "Auto",
    content: @Composable () -> Unit
) {
    val isDarkTheme = when (selectedTheme) {
        "Dark" -> true
        "深色" -> true
        "Light" -> false
        "浅色" -> false
        else -> isSystemInDarkTheme() // Auto跟随系统
    }

    MaterialTheme(
        colorScheme = if (isDarkTheme) DarkColorScheme else LightColorScheme,
        typography = AppTypography,
        content = content
    )
}