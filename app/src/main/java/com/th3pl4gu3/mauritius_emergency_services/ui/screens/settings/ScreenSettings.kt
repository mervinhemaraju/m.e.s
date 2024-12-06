package com.th3pl4gu3.mauritius_emergency_services.ui.screens.settings

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.data.DummyData
import com.th3pl4gu3.mauritius_emergency_services.models.MesAppSettings
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import com.th3pl4gu3.mauritius_emergency_services.models.items.MesLocale
import com.th3pl4gu3.mauritius_emergency_services.models.items.SettingsItem
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesOneActionDialog
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesSettingsSimpleItem
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesSettingsSwitchItem
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesSwipeAbleServiceItem
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme
import com.th3pl4gu3.mauritius_emergency_services.utils.KEYWORD_LOCALE_DEFAULT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


const val TAG = "SCREEN_SETTINGS_TAG"

@Composable
@ExperimentalMaterial3Api
fun ScreenSettings(
    settingsViewModel: SettingsViewModel,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
    scope: CoroutineScope = rememberCoroutineScope()
) {
    val mesAppSettings = settingsViewModel.mesAppSettings.collectAsState(initial = MesAppSettings()).value
    val message = settingsViewModel.messageQueue.collectAsState(initial = null).value
    val services = settingsViewModel.services.collectAsState(initial = listOf()).value
    var openEmergencyButtonItemDialog by remember { mutableStateOf(false) }
    var openAppLanguageDialog by remember { mutableStateOf(false) }

    if (message != null) {
        val displayMessage = if (!message.first.isNullOrEmpty()) {
            stringResource(id = message.second, message.first!!)
        } else {
            stringResource(id = message.second)
        }

        LaunchedEffect(Unit) {
            scope.launch {
                snackBarHostState.showSnackbar(
                    displayMessage
                )
            }
        }

        settingsViewModel.clearMessageQueue()
    }

    SettingsContent(
        scrollState = scrollState,
        modifier = modifier,
        dynamicColorsCheckedChange = { settingsViewModel.updateDynamicColorsSelection(it) },
        emergencyButtonAction = { openEmergencyButtonItemDialog = !openEmergencyButtonItemDialog },
        appLanguageAction = { openAppLanguageDialog = !openAppLanguageDialog },
        resetCacheAction = { settingsViewModel.forceRefreshServices() },
        isDynamicColorsChecked = mesAppSettings.dynamicColorsEnabled
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
                        LocaleListCompat.getEmptyLocaleList()
                    } else {
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
    dynamicColorsCheckedChange: (Boolean) -> Unit,
    emergencyButtonAction: () -> Unit,
    appLanguageAction: () -> Unit,
    resetCacheAction: () -> Unit,
    isDynamicColorsChecked: Boolean,
    scrollState: ScrollState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
    ) {

        SettingsLabel(
            text = stringResource(id = R.string.title_settings_display)
        )

        MesSettingsSwitchItem(
            settingsItem = SettingsItem.DynamicColors,
            onCheckedChange = dynamicColorsCheckedChange,
            isChecked = isDynamicColorsChecked
        )

        SettingsLabel(
            text = stringResource(id = R.string.title_settings_feature)
        )

        MesSettingsSimpleItem(
            settingsItem = SettingsItem.EmergencyButton,
            onClick = emergencyButtonAction
        )

        SettingsLabel(
            text = stringResource(id = R.string.title_settings_language)
        )

        MesSettingsSimpleItem(
            settingsItem = SettingsItem.AppLanguage,
            onClick = appLanguageAction
        )

        SettingsLabel(
            text = stringResource(id = R.string.title_settings_cache)
        )

        MesSettingsSimpleItem(
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
    MesOneActionDialog(
        title = stringResource(id = R.string.title_language_selector_dialog),
        onDismissRequest = dismissAction,
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
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
        confirmButtonAction = dismissAction,
        confirmButtonLabel = stringResource(id = R.string.action_close)
    )
}

@Composable
@ExperimentalMaterial3Api
fun EmergencyButtonItemDialog(
    emergencyServices: List<Service>,
    updateEmergencyButtonAction: (service: Service) -> Unit,
    dismissAction: () -> Unit
) {
    MesOneActionDialog(
        title = stringResource(id = R.string.title_emergency_button_selector_dialog),
        onDismissRequest = dismissAction,
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                items(emergencyServices) { service ->

                    MesSwipeAbleServiceItem(
                        service = service,
                        onClick = {
                            updateEmergencyButtonAction(service)
                            dismissAction()
                        },
                        actionVisible = false,
                        extrasClickAction = {_,_ ->},
                        enableSwipeAction = false
                    )
                }
            }
        },
        confirmButtonAction = dismissAction,
        confirmButtonLabel = stringResource(id = R.string.action_close)
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
            dynamicColorsCheckedChange = {},
            emergencyButtonAction = {},
            appLanguageAction = {},
            resetCacheAction = {},
            scrollState = rememberScrollState(),
            isDynamicColorsChecked = true,
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