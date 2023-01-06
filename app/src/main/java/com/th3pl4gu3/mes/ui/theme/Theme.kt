package com.th3pl4gu3.mes.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val MesColorDark = darkColorScheme(
    primary = greenDark,
    onPrimary = greenDark,
    secondary = green40,
    surface = greenDark,
)

private val MesColorLight = lightColorScheme(
    primary = green,
    onPrimary = green,
    secondary = green40,
    surface = green,
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

    val mesColorScheme = if (darkTheme) MesColorDark else MesColorLight

    MaterialTheme(
        colorScheme = mesColorScheme,
        typography = MesTypography,
        content = content
    )
}