package com.th3pl4gu3.mauritius_emergency_services.ui.screens.services

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import com.th3pl4gu3.mauritius_emergency_services.ui.components.*
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.launchEmailIntent
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme
import kotlinx.coroutines.launch

private const val TAG: String = "SCREEN_SERVICES"

@Composable
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun ScreenServices(
    servicesViewModel: ServicesViewModel,
    retryAction: () -> Unit,
    navigateToPreCall: (service: Service, choseNumber: String) -> Unit,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {

    /** Get the UI State from the view model **/
    val servicesUiState by servicesViewModel.servicesUiState.collectAsState()

    /** Define a modifier with the screen background color **/
    val servicesModifier = modifier.background(MaterialTheme.colorScheme.background)

    /** Launch UI State decisions **/
    ServicesUiStateDecisions(
        servicesUiState = servicesUiState,
        retryAction = retryAction,
        navigateToPreCall = navigateToPreCall,
        listState = listState,
        modifier = servicesModifier,
    )
}

/**
 * The decision make for Ui State
 **/
@Composable
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun ServicesUiStateDecisions(
    servicesUiState: ServicesUiState,
    retryAction: () -> Unit,
    navigateToPreCall: (service: Service, chosenNumber: String) -> Unit,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    when (servicesUiState) {
        is ServicesUiState.Loading -> MesScreenLoading(
            loadingMessage = stringResource(id = R.string.message_loading_services),
            modifier = modifier
        )
        is ServicesUiState.Success -> ServicesList(
            services = servicesUiState.services,
            navigateToPreCall = navigateToPreCall,
            listState = listState,
            modifier = modifier,
        )
        is ServicesUiState.Error -> MesScreenError(
            retryAction = retryAction,
            errorMessage = stringResource(id = R.string.message_error_loading_services_failed),
            modifier = modifier
        )
        is ServicesUiState.NoContent -> MesScreenNoContent(
            message = stringResource(id = R.string.message_services_not_found), modifier = modifier
        )
        is ServicesUiState.NoNetwork -> MesScreenError(
            retryAction = retryAction,
            errorMessage = stringResource(id = R.string.message_internet_connection_needed),
            image = painterResource(id = R.drawable.il_no_network)
        )
    }
}

/**
 * The home screen displaying photo grid.
 */
@Composable
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun ServicesList(
    services: List<Service>,
    navigateToPreCall: (service: Service, chosenNumber: String) -> Unit,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val showScrollToTopButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }

    Box {
        LazyColumn(
            modifier = modifier, state = listState
        ) {
            items(services, key = { it.identifier }) { service ->
                MesServiceItem(service = service,
                    onClick = {
                        Log.i(
                            "pre_call_service",
                            "Launching PreCall with service identifier: ${service.identifier}"
                        )

                        navigateToPreCall(service, service.main_contact.toString())
                    },
                    modifier = Modifier.animateItemPlacement(),
                    extrasClickAction = { this_service, contact: String ->
                        if (contact.isDigitsOnly()) {
                            navigateToPreCall(this_service, contact)
                        } else {
                            context.launchEmailIntent(recipient = contact)
                        }
                    })
            }

            item { Spacer(modifier = Modifier.height(54.dp)) }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            MesAnimatedVisibilitySlideVerticallyContent(
                visibility = showScrollToTopButton
            ) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    }
                    , containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowDropUp,
                        contentDescription = stringResource(id = R.string.action_scroll_up),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Preview("Main Screen Light Preview", showBackground = true)
@Preview("Main Screen Dark Preview", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
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
                    main_contact = x + x + x,
                    emails = listOf(),
                    other_contacts = listOf()
                )
            )
        }

        ServicesUiStateDecisions(
            servicesUiState = ServicesUiState.Success(services = mockData),
            retryAction = {},
            navigateToPreCall = { _, _ -> },
            modifier = modifier,
            listState = rememberLazyListState()
        )
    }
}

@Preview("Loading Light Preview", showBackground = true)
@Preview("Loading Dark Preview", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun LoadingScreenPreview() {
    val modifier = Modifier.background(MaterialTheme.colorScheme.background)

    MesTheme {
        ServicesUiStateDecisions(
            servicesUiState = ServicesUiState.Loading,
            retryAction = {},
            navigateToPreCall = { _, _ -> },
            listState = rememberLazyListState(),
            modifier = modifier
        )
    }
}

@Preview("Error Light Preview", showBackground = true)
@Preview("Error Dark Preview", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun ErrorScreenPreview() {
    val modifier = Modifier.background(MaterialTheme.colorScheme.background)

    MesTheme {
        ServicesUiStateDecisions(
            servicesUiState = ServicesUiState.Error,
            retryAction = {},
            navigateToPreCall = { _, _ -> },
            listState = rememberLazyListState(),
            modifier = modifier
        )
    }
}

@Preview("Main Screen Empty List Light Preview", showBackground = true)
@Preview(
    "Main Screen Empty List Dark Preview", showBackground = true, uiMode = UI_MODE_NIGHT_YES
)
@Composable
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun EmptyServicesScreenPreview() {
    MesTheme {
        val modifier = Modifier.background(MaterialTheme.colorScheme.background)

        ServicesUiStateDecisions(
            servicesUiState = ServicesUiState.NoContent,
            retryAction = {},
            navigateToPreCall = { _, _ -> },
            listState = rememberLazyListState(),
            modifier = modifier
        )
    }
}