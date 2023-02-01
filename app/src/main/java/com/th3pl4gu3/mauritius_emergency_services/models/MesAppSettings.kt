package com.th3pl4gu3.mauritius_emergency_services.models

import kotlinx.serialization.Serializable

@Serializable
data class MesAppSettings (
    val appTheme: AppTheme = AppTheme.FOLLOW_SYSTEM,
    val isFirstTimeLogging: Boolean = true,
    val emergencyButtonActionIdentifier: String = ""
)

enum class AppTheme {
    FOLLOW_SYSTEM,
    LIGHT,
    DARK
}