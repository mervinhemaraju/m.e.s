package com.th3pl4gu3.mauritius_emergency_services.ui.extensions

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import com.th3pl4gu3.mauritius_emergency_services.utils.DEFAULT_LOCALE

fun Typography.defaultFontFamily(
    primaryFontFamily: FontFamily,
    secondaryFontFamily: FontFamily
): Typography {
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

fun String.capitalize(): String =
    split(" ").joinToString(" ") { w -> w.replaceFirstChar { c -> c.uppercaseChar() } }

fun Enum<*>.toReadableText(): String {
    return this.name.lowercase().replace("_", " ").capitalize()
}

val Int.isTollFree: Boolean
    get() = this.toString().count() < 5 || this.toString().startsWith("8", true)

val GetAppLocale: String
    get() = if (!AppCompatDelegate.getApplicationLocales().isEmpty) {
        AppCompatDelegate.getApplicationLocales()[0].toString()
    } else {
        DEFAULT_LOCALE
    }