package com.th3pl4gu3.mes.data.store

import com.th3pl4gu3.mes.models.AppTheme
import com.th3pl4gu3.mes.models.MesAppSettings
import kotlinx.coroutines.flow.Flow

interface StoreRepository {

    suspend fun unsetFirstTimeLogging()

    suspend fun updateEmergencyButtonActionIdentifier(identifier: String)

    suspend fun updateTheme(theme: AppTheme)

    fun fetch(): Flow<MesAppSettings>
}