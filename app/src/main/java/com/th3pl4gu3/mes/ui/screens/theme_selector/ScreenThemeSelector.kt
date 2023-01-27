package com.th3pl4gu3.mes.ui.screens.theme_selector

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mes.models.AppTheme
import com.th3pl4gu3.mes.ui.extensions.toReadableText
import com.th3pl4gu3.mes.ui.theme.MesTheme


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
            Text(text = "Title")
        },
        text = {
            Column {
                AppTheme.values().forEach {
                    Surface(
                        onClick = { updateTheme(it) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            RadioButton(
                                selected = it == currentAppTheme,
                                onClick = { updateTheme(it) },
                                modifier = Modifier.weight(0.1f)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = it.toReadableText(),
                                modifier = Modifier.weight(0.9f)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { dialogState() }) {
                Text(text = "Close")
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
            updateTheme = {} ,
            currentAppTheme = AppTheme.LIGHT
        )
    }
}