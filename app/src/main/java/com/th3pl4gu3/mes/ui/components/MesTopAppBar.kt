package com.th3pl4gu3.mes.ui.components

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.ui.theme.MesTheme

@Composable
@ExperimentalMaterial3Api
internal fun MesTopAppBar(
    openDrawer: () -> Unit,
    showSearchIcon: Boolean,
    modifier: Modifier = Modifier,
    searchValue: String,
    searchValueChange: (String) -> Unit,
    hasScrolled: Boolean,
    topAppBarState: TopAppBarState = rememberTopAppBarState(),
    scrollBehavior: TopAppBarScrollBehavior? =
        TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
) {
    var searchActivated by remember {
        mutableStateOf(false)
    }

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.surface
        ),
        title = {
            AppBarTitleContent(
                isSearchBarActivate = searchActivated && showSearchIcon,
                searchValue = searchValue,
                searchValueChange = searchValueChange,
                focusManager = LocalFocusManager.current
            )
        },
        navigationIcon = {
            Crossfade(targetState = searchActivated && showSearchIcon) { isChecked ->
                if (!isChecked) {
                    IconButton(onClick = openDrawer) {
                        MesIcon(
                            painterResource = R.drawable.ic_mes,
                            contentDescription = R.string.ctnt_desc_drawer_open,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                } else {
                    IconButton(enabled = false, onClick = {}) {
                        MesIcon(
                            imageVector = Icons.Outlined.Search,
                        )
                    }
                }
            }
        },
        actions = {
            if (showSearchIcon) {
                Crossfade(targetState = searchActivated) { isChecked ->
                    IconButton(onClick = {
                        searchActivated = !isChecked
                    }) {
                        MesIcon(
                            imageVector = if (isChecked) Icons.Outlined.Close else Icons.Outlined.Search,
                            contentDescription = null,
                        )
                    }
                }
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = if (hasScrolled) {
            modifier
                .shadow(AppBarDefaults.TopAppBarElevation)
        } else {
            modifier
        }
    )
}

@Composable
@ExperimentalMaterial3Api
fun AppBarTitleContent(
    isSearchBarActivate: Boolean,
    searchValue: String,
    focusManager: FocusManager,
    searchValueChange: (String) -> Unit
) {

    MesAnimatedVisibilityExpandedHorizontallyContent(visibility = isSearchBarActivate) {
        OutlinedTextField(
            value = searchValue,
            onValueChange = searchValueChange,
            singleLine = true,
            placeholder = {
                Text(text = stringResource(id = R.string.action_search))
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        )
    }

    MesAnimatedVisibilityExpandedHorizontallyContent(visibility = !isSearchBarActivate) {
        Text(
            text = stringResource(id = R.string.app_name_short).lowercase(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
        )
    }
}

@Preview("Top App Bar Light")
@Preview("Top App Bar Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
fun PreviewTopAppBar() {
    MesTheme {
        MesTopAppBar(
            openDrawer = { },
            showSearchIcon = true,
            searchValue = "",
            searchValueChange = {},
            hasScrolled = false
        )
    }
}