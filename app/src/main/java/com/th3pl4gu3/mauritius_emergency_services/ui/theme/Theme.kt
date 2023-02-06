package com.th3pl4gu3.mauritius_emergency_services.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

//internal val MesColorDark = darkColorScheme(
//    primary = Red100,
//    onPrimary = Gray500,
//    inversePrimary = Color.White,
//
//    secondary = Red200,
//    tertiary = Red300,
//
//    background = Gray500,
//    onBackground = Red100,
//
//    surface = Gray600,
//    surfaceVariant = Gray600,
//    onSurface = Gray50,
//    onSurfaceVariant = Gray200,
//
//    inverseSurface = Gray50,
//    inverseOnSurface = Gray600,
//
//    // Outline Variants
//    outlineVariant = Red300
//)

private val MesColorDark = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)

//internal val MesColorLight = lightColorScheme(
//    // Main color palettes
//    primary = BlueA100,
//    onPrimary = WhiteBlue10,
//    inversePrimary = Gray600,
//
//    secondary = Red400,
//    tertiary = Blue900,
//
//    // Background variants
//    background = Color.White,
//    onBackground = BlueA100,
//
//    // Surface variants
//    surface = Blue20,
//    surfaceVariant = Blue20,
//    onSurface = BlueA100,
//    onSurfaceVariant = Gray600,
//
//    inverseSurface = Gray600,
//    inverseOnSurface = Gray50,
//
//    // Outline Variants
//    outlineVariant = Red500,
//)

private val MesColorLight = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)

@Composable
fun MesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
//    val mesColorScheme =
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        } else {
//            if (darkTheme) MesColorDark else MesColorLight
//        }

    val systemUiController = rememberSystemUiController()

    val mesColorScheme = if (darkTheme) {
        systemUiController.setSystemBarsColor(
            color = MesColorDark.background
        )
        MesColorDark
    } else {
        systemUiController.setSystemBarsColor(
            color = MesColorLight.background
        )
        MesColorLight
    }

    MaterialTheme(
        colorScheme = mesColorScheme,
        typography = MesTypography,
        shapes = MesShapes,
        content = content
    )
}