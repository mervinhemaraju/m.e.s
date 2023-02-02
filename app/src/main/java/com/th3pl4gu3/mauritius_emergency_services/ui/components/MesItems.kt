package com.th3pl4gu3.mauritius_emergency_services.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.th3pl4gu3.mauritius_emergency_services.BuildConfig
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.models.AboutInfoDrawable
import com.th3pl4gu3.mauritius_emergency_services.models.AboutInfoVector
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import com.th3pl4gu3.mauritius_emergency_services.models.SettingsItem
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.capitalize
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme

@Composable
@ExperimentalMaterial3Api
fun MesNavigationDrawerItem(
    label: String,
    icon: @Composable () -> Unit,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    badge: @Composable () -> Unit = {}
) {
    NavigationDrawerItem(
        label = { Text(text = label) },
        icon = icon,
        selected = selected,
        onClick = onClick,
        modifier = modifier.padding(end = 16.dp),
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
            unselectedContainerColor = MaterialTheme.colorScheme.background,
            unselectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.primary
        ),
        badge = badge,
        shape = RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp)
    )
}

@Composable
@ExperimentalMaterial3Api
fun MesEmergencyItem(
    service: Service,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.medium,
        onClick = onClick,
        modifier = modifier.padding(8.dp)
    ) {

        ConstraintLayout(
            modifier = modifier.width(240.dp)
        ) {

            val (
                textTitle,
                emergencyIcon
            ) = createRefs()


            Text(
                text = service.name.capitalize(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = modifier
                    .constrainAs(textTitle) {
                        with(16.dp) {
                            top.linkTo(parent.top, this)
                            start.linkTo(parent.start, this)
                            end.linkTo(parent.end, this)
                            bottom.linkTo(emergencyIcon.top, this)
                        }
                    },
            )

            MesAsyncRoundedImage(
                service = service,
                modifier = modifier
                    .padding(8.dp)
                    .constrainAs(emergencyIcon) {
                        with(16.dp) {
                            bottom.linkTo(parent.bottom, this)
                            start.linkTo(parent.start, this)
                            end.linkTo(parent.end, this)
                        }
                    },
            )
        }
    }

}

@Composable
@ExperimentalMaterial3Api
fun MesAboutItem(
    aboutApp: AboutInfoDrawable,
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

            Image(
                painter = painterResource(id = aboutApp.icon),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(icon) {
                        bottom.linkTo(parent.bottom, 16.dp)
                        top.linkTo(parent.top, 16.dp)
                        start.linkTo(parent.start, 16.dp)
                    }
                    .size(48.dp)
            )

            Text(
                text = stringResource(id = aboutApp.title),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.constrainAs(title) {
                    bottom.linkTo(description.top)
                    top.linkTo(icon.top)
                    linkTo(
                        start = icon.end,
                        end = parent.end,
                        startMargin = 12.dp,
                        endMargin = 32.dp,
                        bias = 0F,
                    )
                }
            )

            Text(
                text = stringResource(id = aboutApp.description),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
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
fun MesAboutItem(
    aboutInfo: AboutInfoVector,
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

            Icon(
                imageVector = aboutInfo.icon,
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(icon) {
                        top.linkTo(parent.top, 16.dp)
                        start.linkTo(parent.start, 16.dp)
                    }
                    .size(20.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = stringResource(id = aboutInfo.title),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.constrainAs(title) {
                    bottom.linkTo(icon.bottom)
                    top.linkTo(icon.top)
                    linkTo(
                        start = icon.end,
                        end = parent.end,
                        startMargin = 12.dp,
                        endMargin = 32.dp,
                        bias = 0F,
                    )
                }
            )

            Text(
                text = if (aboutInfo.title != R.string.title_about_version) stringResource(id = aboutInfo.description) else BuildConfig.VERSION_NAME,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.constrainAs(description) {
                    bottom.linkTo(parent.bottom, 16.dp)
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
fun MesSettingsItem(
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
                tint = MaterialTheme.colorScheme.inversePrimary
            )

            Text(
                text = stringResource(id = settingsItem.title),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
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
                color = MaterialTheme.colorScheme.onSurfaceVariant,
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

@Preview("Items Light", showBackground = true)
@Preview("Items Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
fun MesNavigationDrawerItemPreview() {
    val mockDataService = Service(
        identifier = "security-police-direct-1",
        name = "Police Direct Line 1",
        type = "E",
        icon = "https://img.icons8.com/fluent/100/000000/policeman-male.png",
        main_contact = 999,
        emails = listOf(),
        other_contacts = listOf()
    )

    val mockDataAboutInfo1 = AboutInfoVector.supportAndDevelopment.first()

    val mockDataAboutInfo2 = AboutInfoDrawable.developers.first()

    MesTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            Arrangement.SpaceAround
        ) {
            MesNavigationDrawerItem(
                label = "Home",
                icon = { MesIcon(imageVector = Icons.Outlined.Home) },
                selected = false,
                onClick = {}
            )

            MesNavigationDrawerItem(
                label = "About",
                icon = { MesIcon(imageVector = Icons.Outlined.Info) },
                selected = true,
                onClick = {}
            )

            MesEmergencyItem(
                service = mockDataService,
                onClick = {}
            )

            MesAboutItem(
                aboutInfo = mockDataAboutInfo1,
                onClick = {}
            )

            MesAboutItem(
                aboutApp = mockDataAboutInfo2,
                onClick = {}
            )

            MesSettingsItem(
                settingsItem = SettingsItem.ResetCache,
                onClick = {},
            )
        }
    }
}