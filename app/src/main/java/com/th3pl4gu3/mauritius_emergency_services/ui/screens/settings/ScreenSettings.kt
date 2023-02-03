package com.th3pl4gu3.mauritius_emergency_services.ui.screens.settings

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.data.DummyData
import com.th3pl4gu3.mauritius_emergency_services.models.MesLocale
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import com.th3pl4gu3.mauritius_emergency_services.models.SettingsItem
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesServiceItem
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesSettingsItem
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme
import com.th3pl4gu3.mauritius_emergency_services.utils.KEYWORD_LOCALE_DEFAULT


const val TAG = "SCREEN_SETTINGS_TAG"

@Composable
@ExperimentalMaterial3Api
fun ScreenSettings(
    settingsViewModel: SettingsViewModel,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
) {

    val context = LocalContext.current
    val message = settingsViewModel.messageQueue.collectAsState(initial = null).value
    val services = settingsViewModel.services.collectAsState(initial = listOf()).value
    var openEmergencyButtonItemDialog by remember { mutableStateOf(false) }
    var openAppLanguageDialog by remember { mutableStateOf(false) }

    if (message != null) {
        Toast.makeText(
            context,
            if (!message.first.isNullOrEmpty()) {
                stringResource(id = message.second, message.first!!)
            } else {
                stringResource(id = message.second)
            },
            Toast.LENGTH_SHORT
        ).show()

        settingsViewModel.clearMessageQueue()
    }

    SettingsContent(
        scrollState = scrollState,
        modifier = modifier,
        emergencyButtonAction = { openEmergencyButtonItemDialog = !openEmergencyButtonItemDialog },
        appLanguageAction = { openAppLanguageDialog = !openAppLanguageDialog },
        resetCacheAction = { settingsViewModel.forceRefreshServices() }
    )


    if (openEmergencyButtonItemDialog) {
        EmergencyButtonItemDialog(
            emergencyServices = services,
            dismissAction = {
                openEmergencyButtonItemDialog = !openEmergencyButtonItemDialog
            },
            updateEmergencyButtonAction = { settingsViewModel.updateEmergencyButtonAction(it) }
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
                    if (it.lowercase() == KEYWORD_LOCALE_DEFAULT) {
                        Log.i(TAG, "Setting default locale")

                        LocaleListCompat.getEmptyLocaleList()
                    } else {
                        Log.i(TAG, "Setting locale: $it")
                        LocaleListCompat.forLanguageTags(it)
                    }
                )

                settingsViewModel.forceRefreshServices(silent = true)

                settingsViewModel.sendMessageInQueue(
                    R.string.message_app_language_updated
                )
            }
        )
    }
}

@Composable
@ExperimentalMaterial3Api
fun SettingsContent(
    emergencyButtonAction: () -> Unit,
    appLanguageAction: () -> Unit,
    resetCacheAction: () -> Unit,
    scrollState: ScrollState,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
    ) {

        SettingsLabel(
            text = stringResource(id = R.string.title_settings_feature)
        )

        MesSettingsItem(
            settingsItem = SettingsItem.EmergencyButton,
            onClick = emergencyButtonAction
        )

        SettingsLabel(
            text = stringResource(id = R.string.title_settings_language)
        )

        MesSettingsItem(
            settingsItem = SettingsItem.AppLanguage,
            onClick = appLanguageAction
        )

        SettingsLabel(
            text = stringResource(id = R.string.title_settings_cache)
        )

        MesSettingsItem(
            settingsItem = SettingsItem.ResetCache,
            onClick = resetCacheAction
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
                    text = stringResource(id = R.string.title_language_selector_dialog)
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
                    stringResource(id = R.string.action_close)
                )
            }
        }
    )
}

@Composable
@ExperimentalMaterial3Api
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
                    text = stringResource(id = R.string.title_settings_page_emergency_button_action)
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
                    stringResource(id = R.string.action_close)
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
        SettingsContent(
            emergencyButtonAction = {},
            appLanguageAction = {},
            resetCacheAction = {},
            scrollState = rememberScrollState(),
        )
    }
}

@Preview("Settings App Language Dialog Light Preview", showBackground = true)
@Preview(
    "Settings App Language Dialog Dark Preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
@ExperimentalMaterial3Api
fun AppLanguageDialogPreview() {
    MesTheme {
        AppLanguageDialog(
            languages = MesLocale.Load,
            updateLanguageAction = {},
            dismissAction = {}
        )
    }
}

@Preview("Settings Emergency Button  Dialog Light Preview", showBackground = true)
@Preview(
    "Settings Emergency Button Dialog Dark Preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
@ExperimentalMaterial3Api
fun EmergencyButtonDialogPreview() {
    MesTheme {
        EmergencyButtonItemDialog(
            emergencyServices = DummyData.services,
            updateEmergencyButtonAction = {},
            dismissAction = {}
        )
    }
}