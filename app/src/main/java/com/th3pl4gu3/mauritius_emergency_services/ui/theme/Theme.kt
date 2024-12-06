package com.th3pl4gu3.mauritius_emergency_services.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.th3pl4gu3.mauritius_emergency_services.models.AppColorContrast

@Composable
fun MesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colorContrast: AppColorContrast = AppColorContrast.DEFAULT,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> {
            when (colorContrast) {
                AppColorContrast.DEFAULT -> darkScheme
                AppColorContrast.MEDIUM -> mediumContrastDarkColorScheme
                AppColorContrast.HIGH -> highContrastDarkColorScheme
            }
        }

        else -> {
            when (colorContrast) {
                AppColorContrast.DEFAULT -> lightScheme
                AppColorContrast.MEDIUM -> mediumContrastLightColorScheme
                AppColorContrast.HIGH -> highContrastLightColorScheme
            }
        }
    }

    // Update the system bar colors
    rememberSystemUiController().setSystemBarsColor(
        color = colorScheme.background
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MesTypography,
        shapes = MesShapes,
        content = content
    )
}

