package com.th3pl4gu3.mauritius_emergency_services.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import com.th3pl4gu3.mauritius_emergency_services.models.items.AboutInfoDrawable
import com.th3pl4gu3.mauritius_emergency_services.models.items.AboutInfoVector
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.capitalize
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme

@Composable
@ExperimentalMaterial3Api
fun MesNavigationItem(
    label: String,
    icon: @Composable () -> Unit,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    badge: @Composable () -> Unit = {},
    isRail: Boolean = false
) {
    if (isRail) {
        NavigationRailItem(
            selected = selected,
            onClick = onClick,
            icon = icon,
            label = { Text(text = label) },
            alwaysShowLabel = false,
            modifier = modifier,
            colors = NavigationRailItemDefaults.colors(
                selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                unselectedTextColor = MaterialTheme.colorScheme.onBackground,
                unselectedIconColor = MaterialTheme.colorScheme.onBackground
            )
        )
    } else {
        NavigationDrawerItem(
            label = { Text(text = label) },
            icon = icon,
            selected = selected,
            onClick = onClick,
            modifier = modifier.padding(end = 16.dp),
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                unselectedContainerColor = MaterialTheme.colorScheme.background,
                unselectedTextColor = MaterialTheme.colorScheme.onBackground,
                unselectedIconColor = MaterialTheme.colorScheme.onBackground
            ),
            badge = badge,
            shape = RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp)
        )
    }
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
        elevation = CardDefaults.outlinedCardElevation(
            0.7.dp
        ),
        onClick = onClick,
        modifier = modifier
            .padding(8.dp)
    ) {

        Column(
            modifier = modifier
                .width(240.dp)
                .height(180.dp)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = service.name.capitalize(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                MesAsyncRoundedImage(
                    service = service
                )
            }
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
                color = MaterialTheme.colorScheme.primary,
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
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                text = stringResource(id = aboutInfo.title),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
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
                color = MaterialTheme.colorScheme.secondary,
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
            MesNavigationItem(
                label = "Home",
                icon = { MesIcon(imageVector = Icons.Outlined.Home) },
                selected = false,
                onClick = {}
            )

            MesNavigationItem(
                label = "About",
                icon = { MesIcon(imageVector = Icons.Outlined.Info) },
                selected = true,
                onClick = {}
            )

            MesNavigationItem(
                label = "About",
                icon = { MesIcon(imageVector = Icons.Outlined.Info) },
                selected = false,
                onClick = {},
                isRail = true
            )

            MesNavigationItem(
                label = "About",
                icon = { MesIcon(imageVector = Icons.Outlined.Info) },
                selected = true,
                onClick = {},
                isRail = true
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
        }
    }
}