package com.th3pl4gu3.mauritius_emergency_services.data.store

import android.content.Context
import androidx.datastore.dataStore
import com.th3pl4gu3.mauritius_emergency_services.MesApplication
import com.th3pl4gu3.mauritius_emergency_services.models.AppTheme
import com.th3pl4gu3.mauritius_emergency_services.models.MesAppSettings
import com.th3pl4gu3.mauritius_emergency_services.models.MesAppSettingsSerializer
import kotlinx.coroutines.flow.Flow


/**
 * Load the datastore from context using the [MesAppSettingsSerializer] class
 **/
val Context.datastore by dataStore(fileName = "app-settings.json", MesAppSettingsSerializer)

class DataStoreRepository(private val application: MesApplication) : StoreRepository {

    override suspend fun unsetFirstTimeLogging(){
        application.datastore.updateData {
            it.copy(
                isFirstTimeLogging = false
            )
        }
    }

    override suspend fun updateEmergencyButtonActionIdentifier(identifier: String) {
        application.datastore.updateData {
            it.copy(
                emergencyButtonActionIdentifier = identifier
            )
        }
    }

    override suspend fun updateTheme(theme: AppTheme) {
        application.datastore.updateData {
            it.copy(
                appTheme = theme
            )
        }
    }

    override fun fetch(): Flow<MesAppSettings> = application.datastore.data
}