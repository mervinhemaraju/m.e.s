package com.th3pl4gu3.mauritius_emergency_services.models

import com.th3pl4gu3.mauritius_emergency_services.R
import kotlinx.serialization.Serializable

@Serializable
data class MesAppSettings (
    val appTheme: AppTheme = AppTheme.FOLLOW_SYSTEM,
    val appColorContrast: AppColorContrast = AppColorContrast.DEFAULT,
    val isFirstTimeLogging: Boolean = true,
    val dynamicColorsEnabled: Boolean = false,
    val emergencyButtonActionIdentifier: String = ""
)

enum class AppTheme(val stringId: Int) {
    FOLLOW_SYSTEM(R.string.option_theme_follow_system),
    LIGHT(R.string.option_theme_dark),
    DARK(R.string.option_theme_light)
}

enum class AppColorContrast(val stringId: Int) {
    DEFAULT(R.string.option_theme_contrast_default),
    MEDIUM(R.string.option_theme_contrast_medium),
    HIGH(R.string.option_theme_contrast_high)
}