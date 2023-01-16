package com.th3pl4gu3.mes

import android.app.Application
import android.content.Context
import androidx.datastore.dataStore
import com.th3pl4gu3.mes.data.AppContainer
import com.th3pl4gu3.mes.data.DefaultAppContainer
import com.th3pl4gu3.mes.models.AppTheme
import com.th3pl4gu3.mes.models.MesAppSettingsSerializer


/**
 * Load the datastore from context using the [MesAppSettingsSerializer] class
 **/
val Context.datastore by dataStore(fileName = "app-settings.json", MesAppSettingsSerializer)

class MesApplication : Application() {

    /** [AppContainer] instance used by the rest of classes to obtain dependencies **/
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        /** Instantiate the app container from [DefaultAppContainer] **/
        container = DefaultAppContainer(
            context = applicationContext
        )
    }

    internal suspend fun updateTheme(theme: AppTheme){

        /** Updates the app theme **/
        datastore.updateData {
            it.copy(
                appTheme = theme
            )
        }
    }
}