package com.th3pl4gu3.mes.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mes.ui.theme.MesTheme

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
            unselectedContainerColor = MaterialTheme.colorScheme.background,
        ),
        badge = badge,
        shape = RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp)
    )
}

@Preview("Item Light", showBackground = true)
@Preview("Dark Light", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
fun MesNavigationDrawerItemPreview(){
    MesTheme {
        Column {

            MesNavigationDrawerItem(
                label = "Home",
                icon = { MesIcon(imageVector = Icons.Outlined.Home) },
                selected = false ,
                onClick = { /*TODO*/ }
            )

            MesNavigationDrawerItem(
                label = "About",
                icon = { MesIcon(imageVector = Icons.Outlined.Info) },
                selected = true ,
                onClick = { /*TODO*/ }
            )
        }
    }
}