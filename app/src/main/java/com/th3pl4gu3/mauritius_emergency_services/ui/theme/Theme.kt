package com.th3pl4gu3.mauritius_emergency_services.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

internal val MesColorDark = darkColorScheme(
    primary = Red100,
    onPrimary = Gray500,
    inversePrimary = Color.White,

    secondary = Red200,
    tertiary = Red300,

    background = Gray500,
    onBackground = Red100,

    surface = Gray600,
    onSurface = Gray50,
    onSurfaceVariant = Gray200,

    // Outline Variants
    outlineVariant = Red300
)

internal val MesColorLight = lightColorScheme(
    // Main color palettes
    primary = BlueA100,
    onPrimary = WhiteBlue10,
    inversePrimary = Gray600,

    secondary = Red400,
    tertiary = Blue900,

    // Background variants
    background = WhiteBlue10,
    onBackground = BlueA100,

    // Surface variants
    surface = Blue20,
    onSurface = BlueA100,
    onSurfaceVariant = Gray600,

    // Outline Variants
    outlineVariant = Red500,
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