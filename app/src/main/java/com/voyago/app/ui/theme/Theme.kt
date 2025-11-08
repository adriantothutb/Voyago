package com.voyago.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = TravelBlue,
    onPrimary = BackgroundLight,
    primaryContainer = TravelBlueLight,
    onPrimaryContainer = GrayDark,

    secondary = TravelOrange,
    onSecondary = BackgroundLight,
    secondaryContainer = TravelOrangeLight,
    onSecondaryContainer = GrayDark,

    tertiary = TravelGreen,

    background = BackgroundLight,
    onBackground = GrayDark,

    surface = SurfaceLight,
    onSurface = GrayDark,

    surfaceVariant = GrayLight,
    onSurfaceVariant = GrayMedium,
)

private val DarkColorScheme = darkColorScheme(
    primary = TravelBlueLight,
    onPrimary = BackgroundDark,
    primaryContainer = TravelBlueDark,
    onPrimaryContainer = BackgroundLight,

    secondary = TravelOrangeLight,
    onSecondary = BackgroundDark,
    secondaryContainer = TravelOrange,
    onSecondaryContainer = BackgroundLight,

    tertiary = TravelGreen,

    background = BackgroundDark,
    onBackground = BackgroundLight,

    surface = SurfaceDark,
    onSurface = BackgroundLight,

    surfaceVariant = GrayDark,
    onSurfaceVariant = GrayMedium,
)

@Composable
fun VoyagoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}