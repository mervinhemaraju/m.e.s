package com.th3pl4gu3.mes

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.th3pl4gu3.mes.ui.MesApp
import com.th3pl4gu3.mes.ui.extensions.unsetFirstTimeLogging
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @OptIn(
        ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class,
        ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class,
        ExperimentalPagerApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /** Set the screen's window decor **/
        WindowCompat.setDecorFitsSystemWindows(window, false)

        /** Get the application **/
        val application = (application as MesApplication)


        val requestMultiplePermissions = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            Log.i(
                "Permission_Notification_Status",
                "Entered activity result with ${permissions.entries}"
            )

            permissions.entries.first { it.key == Manifest.permission.CALL_PHONE }.also {
                if(it.value){
                    Toast.makeText(this, "Welcome to MES", Toast.LENGTH_SHORT).show()
                    lifecycleScope.launch {
                        application.unsetFirstTimeLogging()
                    }
                }else{
                    finishAffinity()
                }
            }
        }

        /** Launch the Compose App **/
        setContent {
            MesApp(
                requestMultiplePermissions = requestMultiplePermissions,
                application = application,
                widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
            )
        }
    }

    internal fun shouldShowRationale(): Boolean{
        return ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.CALL_PHONE
        )
    }
}

