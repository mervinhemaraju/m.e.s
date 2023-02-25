package com.th3pl4gu3.mauritius_emergency_services.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.ui.navigation.MesDestinations
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme

@ExperimentalMaterial3Api
fun MesNavigationDrawer(
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToServices: () -> Unit,
    navigateToAbout: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToContactUs: () -> Unit,
    toggleThemeDialog: () -> Unit,
    scrollState: ScrollState? = null,
    closeDrawer: () -> Unit = {},
    modifier: Modifier = Modifier
): Pair<@Composable () -> Unit, @Composable () -> Unit> {
    return Pair(
        {
            MesDrawer(
                currentRoute = currentRoute,
                navigateToHome = navigateToHome,
                navigateToServices = navigateToServices,
                navigateToAbout = navigateToAbout,
                navigateToSettings = navigateToSettings,
                navigateToContactUs = navigateToContactUs,
                toggleThemeDialog = toggleThemeDialog,
                closeDrawer = closeDrawer,
                modifier = modifier
            )
        },
        {
            MesNavRail(
                currentRoute = currentRoute,
                navigateToHome = navigateToHome,
                navigateToServices = navigateToServices,
                navigateToAbout = navigateToAbout,
                navigateToSettings = navigateToSettings,
                navigateToContactUs = navigateToContactUs,
                toggleThemeDialog = toggleThemeDialog,
                scrollState = scrollState ?: rememberScrollState(),
                modifier = modifier
            )
        }
    )
}

@Composable
@ExperimentalMaterial3Api
private fun MesNavRail(
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToServices: () -> Unit,
    navigateToAbout: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToContactUs: () -> Unit,
    toggleThemeDialog: () -> Unit,
    scrollState: ScrollState,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.surface,
        header = {
            MesIcon(
                painterResource = R.drawable.ic_mes,
                contentDescription = R.string.app_name_short,
                modifier = Modifier
                    .size(54.dp)
                    .padding(vertical = 12.dp),
                tint = MaterialTheme.colorScheme.surfaceVariant,
            )
        },
        modifier = modifier
    ) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(scrollState)
                .background(Color.Transparent),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            MesNavigationItem(
                label = stringResource(id = R.string.title_drawer_page_home),
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = stringResource(id = R.string.title_drawer_page_home)
                    )
                },
                selected = currentRoute == MesDestinations.SCREEN_HOME,
                onClick = { navigateToHome() },
                isRail = true
            )
            MesNavigationItem(
                label = stringResource(id = R.string.title_drawer_page_services),
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = stringResource(id = R.string.title_drawer_page_services)
                    )
                },
                selected = currentRoute == MesDestinations.SCREEN_SERVICES,
                onClick = { navigateToServices() },
                isRail = true
            )
            MesNavigationItem(
                label = stringResource(id = R.string.title_drawer_page_cyclone_report),
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Cyclone,
                        contentDescription = stringResource(id = R.string.title_drawer_page_cyclone_report)
                    )
                },
                selected = currentRoute == MesDestinations.SCREEN_CYCLONE_REPORT,
                onClick = {},
                isRail = true
            )
            MesNavigationItem(
                label = stringResource(id = R.string.title_drawer_page_about),
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = stringResource(id = R.string.title_drawer_page_about)
                    )
                },
                selected = currentRoute == MesDestinations.SCREEN_ABOUT,
                onClick = { navigateToAbout() },
                isRail = true
            )
            MesNavigationItem(
                label = stringResource(id = R.string.title_drawer_page_settings),
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = stringResource(id = R.string.title_drawer_page_settings)
                    )
                },
                selected = currentRoute == MesDestinations.SCREEN_SETTINGS,
                onClick = { navigateToSettings() },
                isRail = true
            )

            MesNavigationItem(
                label = stringResource(id = R.string.title_drawer_page_choose_theme),
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Brightness4,
                        contentDescription = stringResource(id = R.string.title_drawer_page_choose_theme)
                    )
                },
                selected = currentRoute == MesDestinations.SCREEN_THEME,
                onClick = { toggleThemeDialog() },
                isRail = true
            )

            MesNavigationItem(
                label = stringResource(id = R.string.title_drawer_page_contact_us),
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = stringResource(id = R.string.title_drawer_page_contact_us)
                    )
                },
                selected = currentRoute == MesDestinations.SCREEN_CONTACTUS,
                onClick = { navigateToContactUs() },
                isRail = true
            )
        }
    }
}

@Composable
@ExperimentalMaterial3Api
private fun MesDrawer(
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToServices: () -> Unit,
    navigateToAbout: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToContactUs: () -> Unit,
    toggleThemeDialog: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.background,
        modifier = modifier
    ) {

        MesDrawerHeader(
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp),
        )

        MesNavigationItem(
            label = stringResource(id = R.string.title_drawer_page_home),
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = stringResource(id = R.string.title_drawer_page_home)
                )
            },
            selected = currentRoute == MesDestinations.SCREEN_HOME,
            onClick = { navigateToHome(); closeDrawer() },
        )
        MesNavigationItem(
            label = stringResource(id = R.string.title_drawer_page_services),
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Phone,
                    contentDescription = stringResource(id = R.string.title_drawer_page_services)
                )
            },
            selected = currentRoute == MesDestinations.SCREEN_SERVICES,
            onClick = { navigateToServices(); closeDrawer() },
        )
        MesNavigationItem(
            label = stringResource(id = R.string.title_drawer_page_cyclone_report),
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Cyclone,
                    contentDescription = stringResource(id = R.string.title_drawer_page_cyclone_report)
                )
            },
            selected = currentRoute == MesDestinations.SCREEN_CYCLONE_REPORT,
            onClick = {},
            badge = {
                Badge(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.clip(MaterialTheme.shapes.extraSmall)
                ){
                    Text(
                        text = stringResource(id = R.string.message_item_coming_soon),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        )
        MesNavigationItem(
            label = stringResource(id = R.string.title_drawer_page_about),
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = stringResource(id = R.string.title_drawer_page_about)
                )
            },
            selected = currentRoute == MesDestinations.SCREEN_ABOUT,
            onClick = { navigateToAbout(); closeDrawer() },
        )
        MesNavigationItem(
            label = stringResource(id = R.string.title_drawer_page_settings),
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = stringResource(id = R.string.title_drawer_page_settings)
                )
            },
            selected = currentRoute == MesDestinations.SCREEN_SETTINGS,
            onClick = { navigateToSettings(); closeDrawer() },
        )

        Divider(
            thickness = 0.7.dp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(
                top = 16.dp,
                bottom = 16.dp,
                start = 32.dp,
                end = 32.dp
            )
        )

        MesNavigationItem(
            label = stringResource(id = R.string.title_drawer_page_choose_theme),
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Brightness4,
                    contentDescription = stringResource(id = R.string.title_drawer_page_choose_theme)
                )
            },
            selected = currentRoute == MesDestinations.SCREEN_THEME,
            onClick = { toggleThemeDialog() }
        )

        MesNavigationItem(
            label = stringResource(id = R.string.title_drawer_page_contact_us),
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = stringResource(id = R.string.title_drawer_page_contact_us)
                )
            },
            selected = currentRoute == MesDestinations.SCREEN_CONTACTUS,
            onClick = { navigateToContactUs() },
            badge = {
                MesIcon(
                    imageVector = Icons.Outlined.OpenInNew, contentDescription = stringResource(
                        id = R.string.message_external_link
                    )
                )
            }
        )
    }
}


@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
fun PreviewAppNavRail() {
    MesTheme {
        MesNavigationDrawer(
            currentRoute = MesDestinations.SCREEN_HOME,
            navigateToHome = {},
            navigateToServices = {},
            navigateToAbout = {},
            navigateToSettings = {},
            toggleThemeDialog = {},
            navigateToContactUs = {},
            scrollState = rememberScrollState()
        ).second()
    }
}

@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
fun PreviewAppDrawer() {
    MesTheme {
        MesNavigationDrawer(
            currentRoute = MesDestinations.SCREEN_HOME,
            navigateToHome = {},
            navigateToServices = {},
            navigateToAbout = {},
            navigateToSettings = {},
            toggleThemeDialog = {},
            navigateToContactUs = {},
            closeDrawer = {}
        ).first()
    }
}
