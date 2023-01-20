package com.th3pl4gu3.mes.ui.screens.services

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.models.Service
import com.th3pl4gu3.mes.ui.components.MesAnimatedVisibilitySlideHorizontallyContent
import com.th3pl4gu3.mes.ui.components.MesServiceItem
import com.th3pl4gu3.mes.ui.theme.MesTheme
import kotlinx.coroutines.launch

@Composable
@ExperimentalFoundationApi
fun ScreenServices(
    servicesUiState: ServicesUiState,
    searchBarValue: String,
    retryAction: () -> Unit,
    navigateToPreCall: (service: Service) -> Unit,
    modifier: Modifier = Modifier
) {

    // Define a modifier with the screen background color
    val servicesModifier = modifier.background(MaterialTheme.colorScheme.background)

    // Load the component to show based on the UI State
    when (servicesUiState) {
        is ServicesUiState.Loading -> LoadingScreen(servicesModifier)
        is ServicesUiState.Success -> ServicesList(
            servicesUiState.services,
            searchBarValue,
            navigateToPreCall,
            servicesModifier
        )
        is ServicesUiState.Error -> ErrorScreen(retryAction, servicesModifier)
    }

}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 12.dp,
            modifier = Modifier
                .size(120.dp)
                .padding(16.dp)
        )

        Text(
            text = "Getting services...",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.il_error),
            contentDescription = "",
            modifier = Modifier.padding(32.dp)
        )

        Button(
            onClick = retryAction,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Retry")
        }
    }
}

/**
 * The home screen displaying photo grid.
 */
@Composable
@ExperimentalFoundationApi
fun ServicesList(
    services: List<Service>,
    searchBarValue: String,
    navigateToPreCall: (service: Service) -> Unit,
    modifier: Modifier = Modifier
) {
    if (services.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.il_no_data),
                contentDescription = "",
                modifier = Modifier.padding(32.dp)
            )

            Text(
                text = "No services found",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    } else {

        val state = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        val showScrollToTopButton by remember {
            derivedStateOf {
                state.firstVisibleItemIndex > 0
            }
        }

        Box {
            LazyColumn(
                modifier = modifier,
                state = state
            ) {
                items(
                    services,
                    key = { it.identifier }
                ) { service ->
                    MesServiceItem(
                        service = service,
                        onClick = {
                            Log.i(
                                "pre_call_service",
                                "Launching PreCall with service identifier: ${service.identifier}"
                            )

                            navigateToPreCall(service)
                        },
                        modifier = Modifier.animateItemPlacement()
                    )
                }
            }

            MesAnimatedVisibilitySlideHorizontallyContent(
                visibility = showScrollToTopButton,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
            ) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            state.animateScrollToItem(0)
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowDropUp,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

        }
    }
}

@Preview("Loading Light Preview", showBackground = true)
@Preview("Loading Dark Preview", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoadingScreenPreview() {
    MesTheme {
        val modifier = Modifier.background(MaterialTheme.colorScheme.background)

        LoadingScreen(
            modifier = modifier
        )
    }
}

@Preview("Error Light Preview", showBackground = true)
@Preview("Error Dark Preview", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ErrorScreenPreview() {
    MesTheme {
        val modifier = Modifier.background(MaterialTheme.colorScheme.background)

        ErrorScreen(
            retryAction = {},
            modifier = modifier
        )
    }
}

@Preview("Main Screen Light Preview", showBackground = true)
@Preview("Main Screen Dark Preview", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalFoundationApi
fun AllServicesScreenPreview() {
    MesTheme {
        val mockData = mutableListOf<Service>()
        val modifier = Modifier.background(MaterialTheme.colorScheme.background)

        for (x in 1..10) {
            mockData.add(
                Service(
                    identifier = "id-$x",
                    name = "Police Direct Line $x",
                    type = "E",
                    icon = "https://img.icons8.com/fluent/100/000000/policeman-male.png",
                    number = x + x + x
                )
            )
        }

        ScreenServices(
            servicesUiState = ServicesUiState.Success(services = mockData),
            searchBarValue = "",
            retryAction = {},
            navigateToPreCall = {},
            modifier = modifier
        )
    }
}

@Preview("Main Screen Empty List Light Preview", showBackground = true)
@Preview("Main Screen Empty List Dark Preview", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalFoundationApi
fun EmptyServicesScreenPreview() {
    MesTheme {
        val mockData = mutableListOf<Service>()
        val modifier = Modifier.background(MaterialTheme.colorScheme.background)

        ScreenServices(
            servicesUiState = ServicesUiState.Success(services = mockData),
            searchBarValue = "",
            retryAction = {},
            navigateToPreCall = {},
            modifier = modifier
        )
    }
}