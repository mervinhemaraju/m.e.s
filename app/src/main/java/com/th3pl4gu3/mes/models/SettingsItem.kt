package com.th3pl4gu3.mes.models

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cached
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.WifiTethering
import androidx.compose.ui.graphics.vector.ImageVector
import com.th3pl4gu3.mes.R

sealed class SettingsItem(
    val icon: ImageVector,
    val title: String,
    val description: String
) {
    object EmergencyButton : SettingsItem(
        icon = Icons.Outlined.WifiTethering,
        title = "Emergency Red Button",
        description = "Set an emergency service for the Red button",
    )

    object ResetCache : SettingsItem(
        icon = Icons.Outlined.Cached,
        title = "Clear Cache",
        description = "This will allow Mes to clear cache and re-load the services from the Internet",
    )

    object AppLanguage : SettingsItem(
        icon = Icons.Outlined.Language,
        title = "Change Language",
        description = "Click to change the app's language",
    )
}