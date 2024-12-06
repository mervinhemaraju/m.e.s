package com.th3pl4gu3.mauritius_emergency_services.ui.screens.theme_selector

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.models.AppColorContrast
import com.th3pl4gu3.mauritius_emergency_services.models.AppTheme
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesOneActionDialog
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.toReadableText
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme

@Composable
@ExperimentalMaterial3Api
fun ScreenThemeSelector(
    dialogState: () -> Unit,
    dynamicColorsEnabled: Boolean,
    updateTheme: (app: AppTheme, colorContrast: AppColorContrast) -> Unit,
    currentAppTheme: AppTheme,
    currentColorContrast: AppColorContrast,
    modifier: Modifier = Modifier
) {
    MesOneActionDialog(
        title = stringResource(id = R.string.title_theme_selector_dialog),
        onDismissRequest = { dialogState() },
        content = {
            Column {
                AppTheme.entries.forEach {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = { updateTheme(it, currentColorContrast) },
                                onClickLabel = it.toReadableText()
                            )
                    ) {
                        RadioButton(
                            selected = it == currentAppTheme,
                            onClick = null,
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp),
                            colors = RadioButtonDefaults.colors(
                                unselectedColor = MaterialTheme.colorScheme.secondary,
                                selectedColor = MaterialTheme.colorScheme.primary
                            )
                        )

                        Spacer(
                            modifier = Modifier
                                .width(8.dp)
                        )

                        Text(
                            text = stringResource(it.stringId),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .weight(9f)
                                .padding(8.dp),
                            color = if (it == currentAppTheme) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.section_theme_preferences_contrast),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )

                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    AppColorContrast.entries.map {
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = it.ordinal,
                                count = AppColorContrast.entries.size
                            ),
                            colors = SegmentedButtonDefaults.colors(
                                disabledInactiveContentColor = MaterialTheme.colorScheme.onSurface,
                                disabledInactiveContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                            ),
                            enabled = !dynamicColorsEnabled,
                            onClick = { updateTheme(currentAppTheme, it) },
                            selected = it == currentColorContrast && !dynamicColorsEnabled,
                        ) {
                            Text(
                                text = stringResource(it.stringId),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                if (dynamicColorsEnabled) {
                    Text(
                        text = stringResource(R.string.message_error_color_contrast_disabled_with_dynamics_color),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
            }
        },
        confirmButtonAction = { dialogState() },
        confirmButtonLabel = stringResource(id = R.string.action_close),
        modifier = modifier
    )
}

@Preview("Theme Selector Light", showBackground = true)
@Preview("Theme Selector Dark", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
fun ThemeSelectorScreenPreview() {
    MesTheme {
        ScreenThemeSelector(
            dialogState = {},
            updateTheme = { _, _ -> },
            currentAppTheme = AppTheme.LIGHT,
            currentColorContrast = AppColorContrast.MEDIUM,
            dynamicColorsEnabled = false
        )
    }
}

@Preview("Theme Selector with Dynamics Color Light", showBackground = true)
@Preview(
    "Theme Selector with Dynamics Color Dark",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
@ExperimentalMaterial3Api
fun ThemeSelectorWithDynamicsColorScreenPreview() {
    MesTheme {
        ScreenThemeSelector(
            dialogState = {},
            updateTheme = { _, _ -> },
            currentAppTheme = AppTheme.LIGHT,
            currentColorContrast = AppColorContrast.MEDIUM,
            dynamicColorsEnabled = true
        )
    }
}