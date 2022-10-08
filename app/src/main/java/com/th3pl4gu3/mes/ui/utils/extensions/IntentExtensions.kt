package com.th3pl4gu3.mes.ui.utils.extensions

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.th3pl4gu3.mes.BuildConfig
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.th3pl4gu3.mes.R

/**
 * This file represents extensions for all
 * Android Intents
 */

// Returns an Action Call Intent
inline val String.toPhoneCallIntent: Intent
    get() {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$this")
        return intent
    }

// Returns Application Settings Intent
fun requireAppSettingsIntent() = with(Intent()) {
    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri = Uri.fromParts(
        "package",
        BuildConfig.APPLICATION_ID,
        null
    )
    data = uri
    flags = Intent.FLAG_ACTIVITY_NEW_TASK

    this
}


// Returns a Mail Intent
fun Context.requireMailIntent(subject: String, body: String) = Intent(Intent.ACTION_SENDTO).apply {
    data = Uri.parse("mailto:")
    putExtra(
        Intent.EXTRA_EMAIL,
        arrayOf(this@requireMailIntent.getString(R.string.app_support_email))
    )
    putExtra(Intent.EXTRA_SUBJECT, subject)
    putExtra(Intent.EXTRA_TEXT, body)
}

// Returns a Share Intent
inline val String.toShareIntent: Intent
    get() = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, this@toShareIntent)
        type = "text/plain"

        Intent.createChooser(this@apply, null)
    }

// Returns a Browser Intent
inline val String.toBrowserIntent: Intent
    get() = Intent(Intent.ACTION_WEB_SEARCH).apply {
        putExtra(SearchManager.QUERY, this@toBrowserIntent)
    }

/**
 *  Checks whether the intent has an app that can
 *  handle it.
 *  Should be called before starting an intent
 **/
fun Intent.hasSuccessor(context: Context) = resolveActivity(context.packageManager) != null