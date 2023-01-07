package com.th3pl4gu3.mes.ui.utils

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import java.util.*

fun Typography.defaultFontFamily(primaryFontFamily: FontFamily, secondaryFontFamily: FontFamily): Typography {
    return this.copy(
        displayLarge = this.displayLarge.copy(fontFamily = primaryFontFamily),
        displayMedium = this.displayMedium.copy(fontFamily = primaryFontFamily),
        displaySmall = this.displaySmall.copy(fontFamily = primaryFontFamily),
        headlineLarge = this.headlineLarge.copy(fontFamily = primaryFontFamily),
        headlineMedium = this.headlineMedium.copy(fontFamily = primaryFontFamily),
        headlineSmall = this.headlineSmall.copy(fontFamily = primaryFontFamily),
        titleLarge = this.titleLarge.copy(fontFamily = secondaryFontFamily),
        titleMedium = this.titleMedium.copy(fontFamily = secondaryFontFamily),
        titleSmall = this.titleSmall.copy(fontFamily = primaryFontFamily),
        bodyLarge = this.bodyLarge.copy(fontFamily = primaryFontFamily),
        bodyMedium = this.bodyMedium.copy(fontFamily = primaryFontFamily),
        bodySmall = this.bodySmall.copy(fontFamily = primaryFontFamily),
        labelLarge = this.labelLarge.copy(fontFamily = primaryFontFamily),
        labelMedium = this.labelMedium.copy(fontFamily = primaryFontFamily),
        labelSmall = this.labelSmall.copy(fontFamily = primaryFontFamily)
    )
}

fun String.capitalize(): String {
    return this.lowercase().replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault())
        else it.toString()
    }
}
