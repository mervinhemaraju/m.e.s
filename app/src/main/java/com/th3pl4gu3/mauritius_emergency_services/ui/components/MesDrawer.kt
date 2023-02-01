package com.th3pl4gu3.mauritius_emergency_services.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.ui.navigation.MesDestinations
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme

@Composable
@ExperimentalMaterial3Api
fun MesDrawer(
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

        MesNavigationDrawerItem(
            label = stringResource(id = R.string.title_drawer_page_home),
            icon = { Icon(Icons.Outlined.Home, contentDescription = null) },
            selected = currentRoute == MesDestinations.SCREEN_HOME,
            onClick = { navigateToHome(); closeDrawer() },
        )
        MesNavigationDrawerItem(
            label = stringResource(id = R.string.title_drawer_page_services),
            icon = { Icon(Icons.Outlined.Phone, contentDescription = null) },
            selected = currentRoute == MesDestinations.SCREEN_SERVICES,
            onClick = { navigateToServices(); closeDrawer() },
        )
        MesNavigationDrawerItem(
            label = stringResource(id = R.string.title_drawer_page_about),
            icon = { Icon(Icons.Outlined.Info, contentDescription = null) },
            selected = currentRoute == MesDestinations.SCREEN_ABOUT,
            onClick = { navigateToAbout(); closeDrawer() },
        )
        MesNavigationDrawerItem(
            label = stringResource(id = R.string.title_drawer_page_settings),
            icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
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

        MesNavigationDrawerItem(
            label = stringResource(id = R.string.title_drawer_page_choose_theme),
            icon = { Icon(Icons.Outlined.Brightness4, contentDescription = null) },
            selected = currentRoute == MesDestinations.SCREEN_THEME,
            onClick = { toggleThemeDialog() }
        )

        MesNavigationDrawerItem(
            label = stringResource(id = R.string.title_drawer_page_contact_us),
            icon = { Icon(Icons.Outlined.Email, contentDescription = null) },
            selected = currentRoute == MesDestinations.SCREEN_CONTACTUS,
            onClick = { navigateToContactUs() },
            badge = { MesIcon(Icons.Outlined.OpenInNew, contentDescription = null) }
        )
    }
}

@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
fun PreviewAppDrawer() {
    MesTheme {
        MesDrawer(
            currentRoute = MesDestinations.SCREEN_HOME,
            navigateToHome = {},
            navigateToServices = {},
            navigateToAbout = {},
            navigateToSettings = {},
            toggleThemeDialog = {},
            navigateToContactUs = {},
            closeDrawer = {}
        )
    }
}
