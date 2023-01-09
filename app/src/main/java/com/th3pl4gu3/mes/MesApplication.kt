package com.th3pl4gu3.mes

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.datastore.dataStore
import com.th3pl4gu3.mes.data.network.AppContainer
import com.th3pl4gu3.mes.data.network.DefaultAppContainer
import com.th3pl4gu3.mes.models.AppTheme
import com.th3pl4gu3.mes.models.MesAppSettingsSerializer


val Context.datastore by dataStore(fileName = "app-settings.json", MesAppSettingsSerializer)

class MesApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }

    internal suspend fun updateTheme(theme: AppTheme){
        datastore.updateData {
            it.copy(
                appTheme = theme
            )
        }
    }
}