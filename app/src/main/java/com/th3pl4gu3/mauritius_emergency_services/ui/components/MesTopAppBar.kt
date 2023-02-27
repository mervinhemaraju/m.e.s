package com.th3pl4gu3.mauritius_emergency_services.ui.components

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme

@Composable
@ExperimentalMaterial3Api
internal fun MesTopAppBar(
    openDrawer: () -> Unit,
    showSortAction: Boolean,
    modifier: Modifier = Modifier,
    hasScrolled: Boolean,
    topAppBarState: TopAppBarState = rememberTopAppBarState(),
    scrollBehavior: TopAppBarScrollBehavior? = TopAppBarDefaults.enterAlwaysScrollBehavior(
        topAppBarState
    )
) {
//    var searchActivated by remember {
//        mutableStateOf(false)
//    }

    CenterAlignedTopAppBar(colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.background,
        scrolledContainerColor = MaterialTheme.colorScheme.surface
    ), title = {
        AppBarTitleContent(
//                isSearchBarActivate = searchActivated && showSearchIcon,
//                searchValue = searchValue,
//                searchValueChange = searchValueChange,
//                focusManager = LocalFocusManager.current
        )
    }, navigationIcon = {
//            Crossfade(targetState = searchActivated && showSearchIcon) { isChecked ->
//                if (!isChecked) {
//                    IconButton(onClick = openDrawer) {
//                        MesIcon(
//                            painterResource = R.drawable.ic_mes,
//                            contentDescription = R.string.ctnt_desc_drawer_open,
//                            modifier = Modifier.size(32.dp)
//                        )
//                    }
//                } else {
//                    IconButton(enabled = false, onClick = {}) {
//                        MesIcon(
//                            imageVector = Icons.Outlined.Search,
//                            contentDescription = stringResource(id = R.string.action_search)
//                        )
//                    }
//                }
//            }
//            IconButton(onClick = openDrawer) {
//                MesIcon(
//                    painterResource = R.drawable.ic_mes,
//                    contentDescription = R.string.ctnt_desc_drawer_open,
//                    modifier = Modifier.size(32.dp)
//                )
//            }
        IconButton(
            onClick = openDrawer,
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.large
                )
        ) {
            MesIcon(
                painterResource = R.drawable.ic_mes,
                contentDescription = R.string.ctnt_desc_drawer_open,
                modifier = Modifier.size(32.dp)
            )
        }
    }, actions = {
        if (showSortAction) {
//                Crossfade(targetState = searchActivated) { isChecked ->
//                    IconButton(onClick = {
//                        searchActivated = !isChecked
//                    }) {
//MesIcon(
//                    imageVector = Icons.Outlined.Sort,
//                    contentDescription = stringResource(id = R.string.action_sort)
//                )
//                    }
//                }
//            IconButton(onClick = {}) {
//                MesIcon(
//                    imageVector = Icons.Outlined.Sort,
//                    contentDescription = stringResource(id = R.string.action_sort)
//                )
//            }
            IconButton(
                onClick = {},
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        shape = MaterialTheme.shapes.large
                    )
            ) {
                MesIcon(
                    imageVector = Icons.Outlined.Sort,
                    contentDescription = stringResource(id = R.string.action_sort)
                )
            }
        }
    }, scrollBehavior = scrollBehavior, modifier = if (hasScrolled) {
        modifier.shadow(AppBarDefaults.TopAppBarElevation)
    } else {
        modifier
    }
    )
}

@Composable
@ExperimentalMaterial3Api
fun AppBarTitleContent(
//    isSearchBarActivate: Boolean,
//    searchValue: String,
//    focusManager: FocusManager,
//    searchValueChange: (String) -> Unit
) {

//    MesAnimatedVisibilityExpandedHorizontallyContent(visibility = isSearchBarActivate) {
//        OutlinedTextField(
//            value = searchValue,
//            onValueChange = searchValueChange,
//            singleLine = true,
//            placeholder = {
//                Text(text = stringResource(id = R.string.action_search))
//            },
//            colors = TextFieldDefaults.textFieldColors(
//                containerColor = Color.Transparent,
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent,
//                textColor = MaterialTheme.colorScheme.primary,
//                placeholderColor = MaterialTheme.colorScheme.secondary
//            ),
//            textStyle = MaterialTheme.typography.bodyLarge,
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentWidth(align = Alignment.CenterHorizontally)
//                .animateContentSize(
//                    animationSpec = spring(
//                        dampingRatio = Spring.DampingRatioMediumBouncy,
//                        stiffness = Spring.StiffnessLow
//                    )
//                ),
//            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//        )
//    }

//    MesAnimatedVisibilityExpandedHorizontallyContent(visibility = !isSearchBarActivate) {
//        Text(
//            text = stringResource(id = R.string.app_name_short).lowercase(),
//            style = MaterialTheme.typography.titleLarge,
//            color = MaterialTheme.colorScheme.onSurface,
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentWidth(align = Alignment.CenterHorizontally)
//        )
//    }
    Text(
        text = stringResource(id = R.string.app_name_short).lowercase(),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
    )
}

@Preview("Top App Bar Light")
@Preview("Top App Bar Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
fun PreviewTopAppBar() {
    MesTheme {
        MesTopAppBar(
            openDrawer = { }, showSortAction = true, hasScrolled = false
        )
    }
}