package com.th3pl4gu3.mauritius_emergency_services.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.th3pl4gu3.mauritius_emergency_services.models.items.SettingsItem
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme


@Composable
@ExperimentalMaterial3Api
fun MesSettingsSimpleItem(
    settingsItem: SettingsItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.Transparent,
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
    ) {
        ConstraintLayout(
            modifier = modifier
                .background(Color.Transparent)
                .fillMaxWidth()
        ) {

            val (
                icon,
                title,
                description
            ) = createRefs()

            MesIcon(
                imageVector = settingsItem.icon,
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(icon) {
                        bottom.linkTo(parent.bottom, 16.dp)
                        top.linkTo(parent.top, 16.dp)
                        start.linkTo(parent.start, 16.dp)
                    },
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                text = stringResource(id = settingsItem.title),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.constrainAs(title) {
                    bottom.linkTo(description.top)
                    top.linkTo(icon.top)
                    linkTo(
                        start = icon.end,
                        end = parent.end,
                        startMargin = 32.dp,
                        endMargin = 32.dp,
                        bias = 0F,
                    )
                }
            )

            Text(
                text = stringResource(id = settingsItem.description),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.constrainAs(description) {
                    bottom.linkTo(icon.bottom)
                    top.linkTo(title.bottom, 4.dp)
                    linkTo(
                        start = title.start,
                        end = parent.end,
                        endMargin = 32.dp,
                        bias = 0F,
                    )
                    width = Dimension.fillToConstraints
                }
            )
        }
    }
}


@Composable
@ExperimentalMaterial3Api
fun MesSettingsSwitchItem(
    settingsItem: SettingsItem,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.Transparent,
        modifier = modifier
            .fillMaxWidth()
    ) {
        ConstraintLayout(
            modifier = modifier
                .background(Color.Transparent)
                .fillMaxWidth()
        ) {

            val (
                icon,
                title,
                description,
                action
            ) = createRefs()

            MesIcon(
                imageVector = settingsItem.icon,
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(icon) {
                        bottom.linkTo(parent.bottom, 16.dp)
                        top.linkTo(parent.top, 16.dp)
                        start.linkTo(parent.start, 16.dp)
                    },
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                text = stringResource(id = settingsItem.title),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.constrainAs(title) {
                    bottom.linkTo(description.top)
                    top.linkTo(icon.top)
                    linkTo(
                        start = icon.end,
                        end = action.start,
                        startMargin = 32.dp,
                        endMargin = 16.dp,
                        bias = 0F,
                    )
                }
            )

            Text(
                text = stringResource(id = settingsItem.description),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.constrainAs(description) {
                    bottom.linkTo(icon.bottom)
                    top.linkTo(title.bottom, 4.dp)
                    linkTo(
                        start = title.start,
                        end = action.start,
                        endMargin = 16.dp,
                        bias = 0F,
                    )
                    width = Dimension.fillToConstraints
                }
            )

            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                modifier = Modifier.constrainAs(action){
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(parent.top, 16.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                }
            )
        }
    }
}

@Preview("Settings Items Light", showBackground = true)
@Preview("Settings Items Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
fun MesSettingsItemsPreview() {
    MesTheme {
        Column {
            MesSettingsSimpleItem(
                settingsItem = SettingsItem.ResetCache,
                onClick = {}
            )

            MesSettingsSwitchItem(
                settingsItem = SettingsItem.DynamicColors,
                isChecked = true,
                onCheckedChange = {}
            )
        }
    }
}