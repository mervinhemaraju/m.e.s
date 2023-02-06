package com.th3pl4gu3.mauritius_emergency_services.ui.screens.theme_selector

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.models.AppTheme
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.toReadableText
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme

@Composable
@ExperimentalMaterial3Api
fun ScreenThemeSelector(
    dialogState: () -> Unit,
    updateTheme: (app: AppTheme) -> Unit,
    currentAppTheme: AppTheme,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {
            dialogState()
        },
        title = {
            Text(
                text = stringResource(id = R.string.title_theme_selector_dialog)
            )
        },
        text = {
            Column {
                AppTheme.values().forEach {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = { updateTheme(it) },
                                onClickLabel = it.toReadableText()
                            )
                    ) {
                        RadioButton(
                            selected = it == currentAppTheme,
                            onClick = null,
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp),
                        )

                        Spacer(modifier = Modifier
                            .width(8.dp))

                        Text(
                            text = it.toReadableText(),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .weight(9f)
                                .padding(8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { dialogState() }) {
                Text(text = stringResource(id = R.string.action_close))
            }
        },
        modifier = modifier
    )
}

@Preview("PreCall Screen Loading Light", showBackground = true)
@Preview("PreCall Screen Loading Dark", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
fun PreviewScreenPreCallLoading() {
    MesTheme {
        ScreenThemeSelector(
            dialogState = {},
            updateTheme = {},
            currentAppTheme = AppTheme.LIGHT
        )
    }
}