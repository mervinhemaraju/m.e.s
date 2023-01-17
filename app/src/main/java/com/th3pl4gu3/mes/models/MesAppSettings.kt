package com.th3pl4gu3.mes.models

import kotlinx.serialization.Serializable

@Serializable
data class MesAppSettings (
    val appTheme: AppTheme = AppTheme.FOLLOW_SYSTEM,
    val servicesRefreshDate: String = ""
)

enum class AppTheme {
    FOLLOW_SYSTEM,
    LIGHT,
    DARK
}