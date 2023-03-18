package com.th3pl4gu3.mauritius_emergency_services.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme

@Composable
@ExperimentalMaterial3Api
fun MesBackTopBar(
    screenTitle: String,
    backButtonAction: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = screenTitle,
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.primary
            )
        },
        navigationIcon = {
            IconButton(onClick = backButtonAction) {
                MesIcon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    )
}

@Composable
@ExperimentalMaterial3Api
fun MesSearchTopBar(
    text: String,
    active: Boolean,
    closeSearchBar: () -> Unit,
    openDrawer: () -> Unit,
    onSearchActiveChange: (Boolean) -> Unit,
    onSearchQueryChange: (String) -> Unit,
) {

    val searchBarAdditionalModifier: Modifier = if (active) {
        Modifier
            .fillMaxWidth()
    } else {
        Modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp
            )
    }

    Box(
        Modifier
            .semantics { isContainer = true }
            .zIndex(1f)
            .fillMaxWidth()) {
        SearchBar(
            modifier = searchBarAdditionalModifier
                .align(Alignment.TopCenter),
            query = text,
            onQueryChange = onSearchQueryChange,
            onSearch = { closeSearchBar() },
            active = active,
            onActiveChange = onSearchActiveChange,
            placeholder = { Text("Welcome to Mes") },
            leadingIcon = {
                if (!active)
                    Icon(
                        Icons.Default.Menu,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { openDrawer() }
                    )
                else
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            closeSearchBar()
                        }
                    )
            },
            trailingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null
                )
            },
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(4) { idx ->
                    val resultText = "Suggestion $idx"
                    ListItem(
                        headlineContent = { Text(resultText) },
                        supportingContent = { Text("Additional info") },
                        leadingContent = {
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.clickable {
                            closeSearchBar()
                        }
                    )
                }
            }
        }
    }
}


@Preview("Mes Back Top Bar Light Preview", showBackground = true)
@Preview(
    "Mes Back Top Bar Dark Preview", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun MesBackTopBarPreview() {
    MesTheme {
        MesBackTopBar(
            screenTitle = "Settings",
            backButtonAction = {}
        )
    }
}

@Preview("Mes Top Bar Light Preview", showBackground = true)
@Preview(
    "Mes Top Bar Dark Preview", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun MesSearchTopBarPreview() {
    MesTheme {
        MesSearchTopBar(
            text = "This is a query",
            active = false,
            closeSearchBar = {},
            openDrawer = {},
            onSearchActiveChange = {},
            onSearchQueryChange = {}
        )
    }
}

@Preview("Mes Search Bar Active Light Preview", showBackground = true)
@Preview(
    "Mes Search Bar Active Dark Preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun MesSearchBarActivePreview() {
    MesTheme {
        MesSearchTopBar(
            text = "This is a query",
            active = true,
            closeSearchBar = {},
            openDrawer = {},
            onSearchActiveChange = {},
            onSearchQueryChange = {}
        )
    }
}