package com.th3pl4gu3.mes.ui

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.th3pl4gu3.mes.MesApplication
import com.th3pl4gu3.mes.datastore
import com.th3pl4gu3.mes.models.AppTheme
import com.th3pl4gu3.mes.models.MesAppSettings
import com.th3pl4gu3.mes.ui.screens.starter.ScreenStarter
import com.th3pl4gu3.mes.ui.theme.MesTheme

@Composable
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
fun MesApp(
    requestMultiplePermissions: ActivityResultLauncher<Array<String>>,
    application: MesApplication,
    widthSizeClass: WindowWidthSizeClass
) {
    /**
     * This is the Main MES app that will
     * determine which screen content to show
     **/

    /** Load the app settings from datastore **/
    val appSettings = application.datastore.data.collectAsState(initial = MesAppSettings()).value

    /** Set the correct app theme that the user has set **/
    val darkTheme = when (appSettings.appTheme) {
        AppTheme.FOLLOW_SYSTEM -> isSystemInDarkTheme()
        AppTheme.DARK -> true
        AppTheme.LIGHT -> false
    }

    MesTheme(
        darkTheme = darkTheme // Load the app theme
    ) {

        /** Determine screen content **/
        if (appSettings.isFirstTimeLogging) {
            ScreenStarter(
                application = application,
                requestMultiplePermissions = requestMultiplePermissions
            )
        } else {
            AppContent(
                application = application,
                appSettings = appSettings,
                widthSizeClass = widthSizeClass
            )
        }

    }
}