package com.th3pl4gu3.mes.ui.screens.services

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.models.Service
import com.th3pl4gu3.mes.ui.components.MesAnimatedVisibilitySlideHorizontallyContent
import com.th3pl4gu3.mes.ui.components.MesScreenError
import com.th3pl4gu3.mes.ui.components.MesScreenLoading
import com.th3pl4gu3.mes.ui.components.MesServiceItem
import com.th3pl4gu3.mes.ui.theme.MesTheme
import kotlinx.coroutines.launch

const val TAG = "SCREEN_SERVICES"

@Composable
@ExperimentalFoundationApi
fun ScreenServices(
    servicesUiState: ServicesUiState,
    retryAction: () -> Unit,
    navigateToPreCall: (service: Service) -> Unit,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {

    // Define a modifier with the screen background color
    val servicesModifier = modifier.background(MaterialTheme.colorScheme.background)

    // Load the component to show based on the UI State
    when (servicesUiState) {
        is ServicesUiState.Loading -> MesScreenLoading(
            loadingMessage = stringResource(id = R.string.message_loading_services),
            modifier = servicesModifier
        )
        is ServicesUiState.Success -> ServicesList(
            services = servicesUiState.services,
            navigateToPreCall = navigateToPreCall,
            listState = listState,
            modifier = servicesModifier,
        )
        is ServicesUiState.Error -> MesScreenError(
            retryAction = retryAction,
            errorMessage = stringResource(id = R.string.message_error_loading_services_failed),
            modifier = servicesModifier
        )
    }

}

/**
 * The home screen displaying photo grid.
 */
@Composable
@ExperimentalFoundationApi
fun ServicesList(
    services: List<Service>,
    navigateToPreCall: (service: Service) -> Unit,
    listState: LazyListState,
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
                text = stringResource(id = R.string.message_services_not_found),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    } else {
        val coroutineScope = rememberCoroutineScope()

        val showScrollToTopButton by remember {
            derivedStateOf {
                listState.firstVisibleItemIndex > 0
            }
        }

        Box {
            LazyColumn(
                modifier = modifier,
                state = listState
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
                
                item { Spacer(modifier = Modifier.height(54.dp)) }
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
                            listState.animateScrollToItem(0)
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
@ExperimentalFoundationApi
fun LoadingScreenPreview() {
    MesTheme {
        ScreenServices(
            servicesUiState = ServicesUiState.Loading ,
            retryAction = {},
            navigateToPreCall = {},
            listState = rememberLazyListState()
        )
    }
}

@Preview("Error Light Preview", showBackground = true)
@Preview("Error Dark Preview", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalFoundationApi
fun ErrorScreenPreview() {
    MesTheme {
        ScreenServices(
            servicesUiState = ServicesUiState.Error ,
            retryAction = {},
            navigateToPreCall = {},
            listState = rememberLazyListState()
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
            retryAction = {},
            navigateToPreCall = {},
            modifier = modifier,
            listState = rememberLazyListState()
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
            retryAction = {},
            navigateToPreCall = {},
            listState = rememberLazyListState(),
            modifier = modifier
        )
    }
}