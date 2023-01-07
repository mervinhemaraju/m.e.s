package com.th3pl4gu3.mes.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.ui.theme.MesTheme

@Composable
@ExperimentalMaterial3Api
internal fun MesTopAppBar(
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    topAppBarState: TopAppBarState = rememberTopAppBarState(),
    scrollBehavior: TopAppBarScrollBehavior? =
        TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.background),
        title = {
            MesTitleLogo(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
            )
        },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                MesIcon(
                    imageVector = Icons.Outlined.Phone,
                    contentDescription = R.string.ctnt_desc_drawer_open,
                )
            }
        },
//        actions = {
//            IconButton(onClick = { /* TODO: Open search */ }) {
//                Icon(
//                    imageVector = Icons.Filled.Search,
//                    contentDescription = "Search",
//                    tint = MaterialTheme.colorScheme.onSurface
//                )
//            }
//        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

@Preview("Top App Bar Light")
@Preview("Top App Bar Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
fun PreviewTopAppBar() {
    MesTheme {
        MesTopAppBar(openDrawer = { })
    }
}