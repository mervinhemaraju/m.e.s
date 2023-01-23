package com.th3pl4gu3.mes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.view.WindowCompat
import com.th3pl4gu3.mes.ui.MesApp

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class,
        ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /** Set the screen's window decor **/
        WindowCompat.setDecorFitsSystemWindows(window, false)

        /** Launch the Compose App **/
        setContent {
            MesApp(
                application = (application as MesApplication),
                widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
            )
        }
    }
}

