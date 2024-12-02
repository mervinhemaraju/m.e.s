package com.th3pl4gu3.mauritius_emergency_services.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.models.Service
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
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.primary
            )
        },
        navigationIcon = {
            IconButton(onClick = backButtonAction) {
                MesIcon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
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
    query: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onSearch: (String) -> Unit,
    openDrawer: () -> Unit,
    closeSearch: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    services: List<Service>,
    onServiceClick: (Service) -> Unit,
    onExtrasClick: (Service, String) -> Unit
) {

    Box(
        Modifier
            .fillMaxWidth()
            .semantics { isTraversalGroup = true }) {

        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            tonalElevation = 2.dp,
            expanded = expanded,
            onExpandedChange = onExpandedChange,
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = onSearchQueryChange,
                    onSearch = onSearch,
                    expanded = expanded,
                    onExpandedChange = onExpandedChange,
                    placeholder = { Text("Hinted search text") },
                    leadingIcon = {
                        if (!expanded)
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = null,
                                modifier = Modifier
                                    .clickable { openDrawer() }
                            )
                        else
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    closeSearch()
                                }
                            )
                    },
                    trailingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null
                        )
                    }
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                item {
                    Text(
                        text = if (query.isEmpty()) stringResource(id = R.string.message_services_search) else stringResource(
                            id = R.string.message_services_search_query,
                            query
                        ),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(16.dp),
                    )
                }
                items(
                    services,
                    key = { it.identifier }
                ) { service ->
                    MesServiceItem(
                        service = service,
                        onClick = { onServiceClick(service) },
                        actionVisible = true,
                        extrasClickAction = onExtrasClick
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
            query = "This is a query",
            expanded = false,
            onExpandedChange = {},
            onSearch = {},
            openDrawer = {},
            closeSearch = {},
            onSearchQueryChange = {},
            services = listOf(),
            onServiceClick = {},
            onExtrasClick = { _, _ -> }
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
            query = "This is a query",
            expanded = true,
            onExpandedChange = {},
            onSearch = {},
            openDrawer = {},
            closeSearch = {},
            onSearchQueryChange = {},
            services = listOf(),
            onServiceClick = {},
            onExtrasClick = { _, _ -> }
        )
    }
}