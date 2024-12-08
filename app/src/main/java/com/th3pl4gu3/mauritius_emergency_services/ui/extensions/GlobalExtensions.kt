package com.th3pl4gu3.mauritius_emergency_services.ui.extensions

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatDelegate
import com.th3pl4gu3.mauritius_emergency_services.utils.DEFAULT_LOCALE

fun getContactUsIntent(): Intent {
    return Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(
            Intent.EXTRA_EMAIL,
            arrayOf("th3pl4gu33@gmail.com")
        )
        putExtra(Intent.EXTRA_SUBJECT, "M.E.S :: User Request")
        putExtra(
            Intent.EXTRA_TEXT,
            "Dear M.E.S team,\n [ADD YOUR MESSAGE] \n\n Regards, \n [ADD YOUR NAME]"
        )
    }
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