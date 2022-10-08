package com.th3pl4gu3.mes.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.th3pl4gu3.mes.ui.utils.extensions.updateAppTheme
import com.th3pl4gu3.mes.ui.utils.helpers.LocalStorageManager

class SettingsViewModel(application: Application) : AndroidViewModel(application) {


    private fun save(key: String, value: Any) = with(LocalStorageManager) {
        withSettings(getApplication())
        put(key, value)
    }

    internal fun updateAppTheme(key: String, value: String) {
        /* Save the new selected theme */
        save(key, value)

        /* Sets the theme */
        getApplication<Application>().updateAppTheme()
    }
}