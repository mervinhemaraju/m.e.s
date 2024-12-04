package com.th3pl4gu3.mauritius_emergency_services.activity

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.th3pl4gu3.mauritius_emergency_services.MesApplication
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.ui.MesApp
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.IsConnectedToNetwork
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

// FIXME(Fix Landscape layout for Home: Emergency button disappears on landscape layout)
// FIXME(Fix Landscape layout overall)
// TODO(Improve search screen for services)
// TODO(Move some layout to ListItems)
// TODO(Add a most used services list)
// FEAT(Add towing services)
// FEAT(Add personal doctors services)
// FEAT(Add a home screen widget)
// FEAT(Allow sorting of services)

@AndroidEntryPoint
class MesActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MAIN_ACTIVITY_LOGS"
    }

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
        super.onCreate(savedInstanceState)

        Log.i(
            TAG, "Starting app with app compat ${
                AppCompatDelegate.getApplicationLocales()
            }"
        )

        Log.i(
            TAG, "Is connected to network $IsConnectedToNetwork"
        )

        /**
         * Installs the custom splash screen and
         * waits for proper content to load
         **/
        installSplashScreen().setKeepOnScreenCondition {
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

            /** Checks if content is not null before loading **/
            if (mainViewModel.appSettings.value != null) {
                MesApp(
                    application = application,
                    widthSizeClass = calculateWindowSizeClass(this).widthSizeClass,
                    appSettings = mainViewModel.appSettings.value!!,
                    mainViewModel = mainViewModel
                )
            }
        }
    }
}

