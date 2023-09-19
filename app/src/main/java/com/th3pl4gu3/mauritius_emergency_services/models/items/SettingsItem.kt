package com.th3pl4gu3.mauritius_emergency_services.models.items

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cached
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.WifiTethering
import androidx.compose.ui.graphics.vector.ImageVector
import com.th3pl4gu3.mauritius_emergency_services.R

sealed class SettingsItem(
    val icon: ImageVector,
    @StringRes val title: Int,
    @StringRes val description: Int
) {
    object DynamicColors : SettingsItem(
        icon = Icons.Outlined.Palette,
        title = R.string.title_settings_page_dynamic_colors,
        description = R.string.description_settings_page_dynamic_colors,
    )

    object EmergencyButton : SettingsItem(
        icon = Icons.Outlined.WifiTethering,
        title = R.string.title_settings_page_emergency_button_action,
        description = R.string.description_settings_page_emergency_button_action,
    )

    object ResetCache : SettingsItem(
        icon = Icons.Outlined.Cached,
        title = R.string.title_settings_page_clear_cache,
        description = R.string.description_settings_page_clear_cache,
    )

    object AppLanguage : SettingsItem(
        icon = Icons.Outlined.Language,
        title = R.string.title_settings_page_change_language,
        description = R.string.description_settings_page_change_language,
    )
}