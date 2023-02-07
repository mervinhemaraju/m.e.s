package com.th3pl4gu3.mauritius_emergency_services.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.ui.navigation.MesDestinations
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme

@Composable
@ExperimentalMaterial3Api
fun MesNavRail(
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

@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
fun PreviewAppNavRail() {
    MesTheme {
        MesNavRail(
            currentRoute = MesDestinations.SCREEN_HOME,
            navigateToHome = {},
            navigateToServices = {},
            navigateToAbout = {},
            navigateToSettings = {},
            toggleThemeDialog = {},
            navigateToContactUs = {},
            scrollState = rememberScrollState()
        )
    }
}
