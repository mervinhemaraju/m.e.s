package com.th3pl4gu3.mauritius_emergency_services.data.store

import com.th3pl4gu3.mauritius_emergency_services.models.AppColorContrast
import com.th3pl4gu3.mauritius_emergency_services.models.AppTheme
import com.th3pl4gu3.mauritius_emergency_services.models.MesAppSettings
import kotlinx.coroutines.flow.Flow

interface StoreRepository {

    suspend fun unsetFirstTimeLogging()

    suspend fun updateEmergencyButtonActionIdentifier(identifier: String)

    suspend fun updateTheme(theme: AppTheme)

    suspend fun updateColorContrast(colorContrast: AppColorContrast)

    suspend fun updateDynamicColorsSelection(checked: Boolean)

    fun fetch(): Flow<MesAppSettings>
}