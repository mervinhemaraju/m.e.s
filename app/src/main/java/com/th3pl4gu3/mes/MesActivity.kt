package com.th3pl4gu3.mes

import android.Manifest
import android.app.LocaleManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
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
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.th3pl4gu3.mes.data.DefaultAppContainer
import com.th3pl4gu3.mes.ui.MesApp
import com.th3pl4gu3.mes.ui.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MesActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MAIN_ACTIVITY_LOGS"
    }

    /** DI to get the [SplashViewModel] **/
    private val splashViewModel: SplashViewModel by viewModels()

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

        /**
         * Installs the custom splash screen and
         * waits for proper content to load
         **/
        installSplashScreen().setKeepOnScreenCondition() {
            !splashViewModel.isLoading.value
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
            if (splashViewModel.startDestination.value != null) {
                MesApp(
                    application = application,
                    widthSizeClass = calculateWindowSizeClass(this).widthSizeClass,
                    startDestination = splashViewModel.startDestination.value!!
                )
            }
        }
    }
}

