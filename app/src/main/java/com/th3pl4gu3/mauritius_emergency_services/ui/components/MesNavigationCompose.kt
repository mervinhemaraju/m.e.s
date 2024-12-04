package com.th3pl4gu3.mauritius_emergency_services.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material.icons.outlined.Brightness4
import androidx.compose.material.icons.outlined.Cyclone
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.ui.navigation.MesDestinations
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme

@ExperimentalMaterial3Api
@Composable
fun MesNavigationDrawer(
    modifier: Modifier = Modifier,
    isExpandedView: Boolean,
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToServices: () -> Unit,
    navigateToCycloneReport: () -> Unit,
    navigateToAbout: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToContactUs: () -> Unit,
    toggleThemeDialog: () -> Unit,
    scrollState: ScrollState? = null,
    closeDrawer: () -> Unit = {}
) {
    if (isExpandedView) {
        MesDrawer(
            currentRoute = currentRoute,
            navigateToHome = navigateToHome,
            navigateToServices = navigateToServices,
            navigateToCycloneReport = navigateToCycloneReport,
            navigateToAbout = navigateToAbout,
            navigateToSettings = navigateToSettings,
            navigateToContactUs = navigateToContactUs,
            toggleThemeDialog = toggleThemeDialog,
            closeDrawer = closeDrawer,
            modifier = modifier
        )
    } else {
        MesNavRail(
            currentRoute = currentRoute,
            navigateToHome = navigateToHome,
            navigateToServices = navigateToServices,
            navigateToCycloneReport = navigateToCycloneReport,
            navigateToAbout = navigateToAbout,
            navigateToSettings = navigateToSettings,
            navigateToContactUs = navigateToContactUs,
            toggleThemeDialog = toggleThemeDialog,
            scrollState = scrollState ?: rememberScrollState(),
            modifier = modifier
        )

    }
}

@Composable
@ExperimentalMaterial3Api
private fun MesNavRail(
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToServices: () -> Unit,
    navigateToCycloneReport: () -> Unit,
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

            Spacer(modifier = Modifier.size(16.dp))

            MesIcon(
                painterResource = R.drawable.ic_mes,
                contentDescription = R.string.app_name_short,
                modifier = Modifier
                    .size(54.dp)
                    .padding(vertical = 12.dp),
                tint = MaterialTheme.colorScheme.onSurface,
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
                onClick = { navigateToCycloneReport() },
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
    navigateToCycloneReport: () -> Unit,
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
            badge = {
                Badge(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Text(
                        text = stringResource(R.string.message_item_new),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(2.dp)
                    )
                }
            },
            selected = currentRoute == MesDestinations.SCREEN_CYCLONE_REPORT,
            onClick = { navigateToCycloneReport(); closeDrawer() },
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

        HorizontalDivider(
            modifier = Modifier.padding(
                top = 16.dp,
                bottom = 16.dp,
                start = 32.dp,
                end = 32.dp
            ),
            thickness = 0.7.dp,
            color = MaterialTheme.colorScheme.onSurface
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
                    imageVector = Icons.AutoMirrored.Outlined.OpenInNew,
                    contentDescription = stringResource(
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
            isExpandedView = false,
            currentRoute = MesDestinations.SCREEN_HOME,
            navigateToHome = {},
            navigateToServices = {},
            navigateToCycloneReport = {},
            navigateToAbout = {},
            navigateToSettings = {},
            toggleThemeDialog = {},
            navigateToContactUs = {},
            scrollState = rememberScrollState()
        )
    }
}

@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
fun PreviewAppDrawer() {
    MesTheme {
        MesNavigationDrawer(
            isExpandedView = true,
            currentRoute = MesDestinations.SCREEN_HOME,
            navigateToHome = {},
            navigateToServices = {},
            navigateToCycloneReport = {},
            navigateToAbout = {},
            navigateToSettings = {},
            toggleThemeDialog = {},
            navigateToContactUs = {},
            closeDrawer = {}
        )
    }
}
