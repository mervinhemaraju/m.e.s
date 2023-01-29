package com.th3pl4gu3.mes.ui.screens.settings

import android.app.LocaleManager
import android.content.res.Configuration
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.th3pl4gu3.mes.models.MesLocale
import com.th3pl4gu3.mes.models.Service
import com.th3pl4gu3.mes.models.SettingsItem
import com.th3pl4gu3.mes.ui.components.MesServiceItem
import com.th3pl4gu3.mes.ui.components.MesSettingsItem
import com.th3pl4gu3.mes.ui.theme.MesTheme
import java.util.*

const val TAG = "SCREEN_SETTINGS_TAG"

@Composable
@ExperimentalMaterial3Api
fun ScreenSettings(
    emergencyServices: List<Service>,
    forceRefreshServices: () -> Unit,
    updateEmergencyButtonAction: (service: Service) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
) {

    var openEmergencyButtonItemDialog by remember { mutableStateOf(false) }
    var openAppLanguageDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
    ) {

        SettingsLabel(
            text = "Feature"
        )

        MesSettingsItem(
            settingsItem = SettingsItem.EmergencyButton,
            onClick = { openEmergencyButtonItemDialog = !openEmergencyButtonItemDialog }
        )

        SettingsLabel(
            text = "Language"
        )

        MesSettingsItem(
            settingsItem = SettingsItem.AppLanguage,
            onClick = { openAppLanguageDialog = !openAppLanguageDialog }
        )

        SettingsLabel(
            text = "Cache"
        )

        MesSettingsItem(
            settingsItem = SettingsItem.ResetCache,
            onClick = forceRefreshServices
        )
    }

    if (openEmergencyButtonItemDialog) {

        EmergencyButtonItemDialog(
            emergencyServices = emergencyServices,
            dismissAction = {
                openEmergencyButtonItemDialog = !openEmergencyButtonItemDialog
            },
            updateEmergencyButtonAction = updateEmergencyButtonAction
        )
    }

    if (openAppLanguageDialog) {
        AppLanguageDialog(
            languages = MesLocale.Load,
            dismissAction = {
                openAppLanguageDialog = !openAppLanguageDialog
            },
            updateLanguageAction = {
                AppCompatDelegate.setApplicationLocales(
                    if(it.lowercase() == "default"){
                        LocaleListCompat.getEmptyLocaleList()
                    }else{
                        LocaleListCompat.forLanguageTags(it)
                    }
                )
            }
        )
    }
}

@Composable
fun SettingsLabel(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = Bold,
        modifier = modifier
            .padding(24.dp)
    )
}

@Composable
@ExperimentalMaterial3Api
fun AppLanguageDialog(
    languages: List<MesLocale>,
    updateLanguageAction: (language: String) -> Unit,
    dismissAction: () -> Unit
) {
    AlertDialog(
        onDismissRequest = dismissAction,
        title = {
            Column {
                Text(
                    text = "Choose a Language"
                )
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                items(languages) { language ->
                    Surface(
                        onClick = {
                            updateLanguageAction(language.tag)
                            dismissAction()
                        }
                    ) {
                        Text(text = language.name)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = dismissAction) {
                Text(
                    "Close"
                )
            }
        }
    )
}

@Composable
fun EmergencyButtonItemDialog(
    emergencyServices: List<Service>,
    updateEmergencyButtonAction: (service: Service) -> Unit,
    dismissAction: () -> Unit
) {
    AlertDialog(
        onDismissRequest = dismissAction,
        title = {
            Column {
                Text(
                    text = "Emergency Button Action"
                )
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                items(emergencyServices) { service ->
                    MesServiceItem(
                        service = service,
                        onClick = {
                            updateEmergencyButtonAction(service)
                            dismissAction()
                        },
                        actionVisible = false
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = dismissAction) {
                Text(
                    "Close"
                )
            }
        }
    )
}

@Preview("Settings Screen Light Preview", showBackground = true)
@Preview(
    "Settings Screen Dark Preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
@ExperimentalMaterial3Api
fun SettingsScreenPreview() {
    MesTheme {
        ScreenSettings(
            emergencyServices = listOf(),
            forceRefreshServices = {},
            updateEmergencyButtonAction = {}
        )
    }
}