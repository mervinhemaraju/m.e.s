package com.th3pl4gu3.mauritius_emergency_services.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.th3pl4gu3.mauritius_emergency_services.MesApplication
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.models.AppTheme
import com.th3pl4gu3.mauritius_emergency_services.ui.MesApp
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

// TODO(Improve search screen for services: Add history of searches)
// TODO(Add follow system for automatic apply of color contrast schemes)
// TODO(Make cyclone names available even when there is no cyclone warnings)
// TODO(Add notifications for cyclone warnings)
// FIXME(emergency number doesn't auto call.)
// FIXME(Theme selector on landscape mode)
// FIXME(Fix issue where swiping back from a call and going into pre-call screen again, it tries to perform a call again)
// FIXME(Cache clearing in the app isn't working properly)
// FEAT(Add towing services / doctor services)
// FEAT(Cyclone predictions)
// FEAT(Add a home screen widget)
// FEAT(Allow sorting of services)

@AndroidEntryPoint
class MesActivity : AppCompatActivity() {

    /** DI to get the [MainViewModel] **/
    private val mainViewModel: MainViewModel by viewModels()

    /**
     * Activity result for
     * runtime permission requests
     **/
    private val mRequestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        Log.i(
            "Permission_Notification_Status",
            "Entered activity result with ${permissions.entries}"
        )

        permissions.entries.first { it.key == Manifest.permission.CALL_PHONE }.also {
            if (it.value) {
                Toast.makeText(
                    this,
                    resources.getText(R.string.message_welcome_mes),
                    Toast.LENGTH_SHORT
                ).show()
                lifecycleScope.launch {
                    (application as MesApplication).container.dataStoreServiceRepository.unsetFirstTimeLogging()
                }
            } else {
                finishAffinity()
            }
        }
    }

    val requestMultiplePermissions: ActivityResultLauncher<Array<String>>
        get() = mRequestMultiplePermissions

    @SuppressLint("MissingPermission")
    @OptIn(
        ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class,
        ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class,
        ExperimentalPagerApi::class, ExperimentalAnimationApi::class
    )

    override fun onCreate(savedInstanceState: Bundle?) {

        /** Install the splash screen before onCreate() **/
        val splashScreen = installSplashScreen()

        /** Launch onCreate() **/
        super.onCreate(savedInstanceState)

        /**
         * Waits for proper content to load
         **/
        splashScreen.setKeepOnScreenCondition {
            !mainViewModel.isLoading.value
        }

        /**
         * Set the screen's window decor
         **/
        WindowCompat.setDecorFitsSystemWindows(window, false)

        /**
         * Instantiate the MesApplication
         **/
        val application = application as MesApplication

        /** Launch the Compose App **/
        setContent {
            val activity = LocalContext.current as Activity

            /** Checks if content is not null before loading **/
            if (mainViewModel.appSettings.value != null) {

                val appSettings = mainViewModel.appSettings.value!!

                /** Set the correct app theme that the user has set **/
                val darkTheme = when (appSettings.appTheme) {
                    AppTheme.FOLLOW_SYSTEM -> isSystemInDarkTheme()
                    AppTheme.DARK -> true
                    AppTheme.LIGHT -> false
                }

                MesTheme(
                    dynamicColor = appSettings.dynamicColorsEnabled,
                    darkTheme = darkTheme,
                    colorContrast = appSettings.appColorContrast
                ) {

                    MesApp(
                        application = application,
                        widthSizeClass = calculateWindowSizeClass(this).widthSizeClass,
                        appSettings = mainViewModel.appSettings.value!!,
                        searchOfflineServices = {
                            mainViewModel.searchOfflineServices(it)
                        },
                        services = mainViewModel.services.collectAsState().value,
                        launchIntent = {
                            activity.startActivity(it)
                        }
                    )
                }
            }
        }
    }
}

