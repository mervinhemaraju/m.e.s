package com.th3pl4gu3.mes.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.ui.navigation.MesDestinations
import com.th3pl4gu3.mes.ui.theme.MesTheme

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
        modifier = modifier.background(MaterialTheme.colorScheme.surface),
    ) {
        MesLogo(
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp),
        )
        NavigationDrawerItem(
            label = { Text(text = "Home") },
            icon = { MesIcon(Icons.Outlined.Home, null) },
            selected = currentRoute == MesDestinations.SCREEN_HOME,
            onClick = { navigateToHome(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(text = "Services") },
            icon = { MesIcon(Icons.Outlined.Phone, null) },
            selected = currentRoute == MesDestinations.SCREEN_SERVICES,
            onClick = { navigateToServices(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(text = "About") },
            icon = { MesIcon(Icons.Outlined.Info, null) },
            selected = currentRoute == MesDestinations.SCREEN_ABOUT,
            onClick = { navigateToAbout(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(text = "Settings") },
            icon = { MesIcon(Icons.Outlined.Settings, null) },
            selected = currentRoute == MesDestinations.SCREEN_SETTINGS,
            onClick = { navigateToSettings(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        Divider(
            thickness = 0.7.dp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(
                top = 16.dp,
                bottom = 16.dp,
                start = 32.dp,
                end = 32.dp
            )
        )

        NavigationDrawerItem(
            label = { Text(text = "Choose Theme") },
            icon = { MesIcon(Icons.Outlined.Brightness4, null) },
            selected = currentRoute == MesDestinations.SCREEN_THEME,
            onClick = { toggleThemeDialog() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationDrawerItem(
            label = { Text(text = "Contact Us") },
            icon = { MesIcon(Icons.Outlined.Email, null) },
            selected = currentRoute == MesDestinations.SCREEN_CONTACTUS,
            onClick = { navigateToContactUs() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
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
