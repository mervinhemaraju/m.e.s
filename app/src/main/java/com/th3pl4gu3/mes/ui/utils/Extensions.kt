package com.th3pl4gu3.mes.ui.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import com.th3pl4gu3.mes.models.AppTheme
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

fun String.capitalize(): String =
    split(" ").joinToString(" ") { w -> w.replaceFirstChar { c ->  c.uppercaseChar() } }



fun Activity.launchContactUsIntent() {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(
            Intent.EXTRA_EMAIL,
            arrayOf("th3pl4gu33@gmail.com")
        )
        putExtra(Intent.EXTRA_SUBJECT, "M.E.S :: User Request")
        putExtra(Intent.EXTRA_TEXT, "Dear M.E.S team,\n [ADD YOUR MESSAGE] \n\n Regards, \n [ADD YOUR NAME]")
    }
    startActivity(intent)
}

fun Enum<*>.toReadableText(): String {
    return this.name.lowercase().replace("_", " ").capitalize()
}