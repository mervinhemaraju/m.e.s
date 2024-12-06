package com.th3pl4gu3.mauritius_emergency_services.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.data.DummyData
import com.th3pl4gu3.mauritius_emergency_services.models.MesAppSettings
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesEmergencyButton
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesEmergencyItem
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesScreenAnimatedLoading
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesScreenError
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme

@Composable
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun ScreenHome(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    navigateToPreCall: (service: Service, chosenNumber: String) -> Unit,
    scrollState: ScrollState = rememberScrollState(),
) {

    // Get the HomeUiState from the view model
    val homeUiState: HomeUiState by homeViewModel.homeUiState.collectAsState()

    // Get the mes app settings from view model
    val mesAppSettings =
        homeViewModel.mesAppSettings.collectAsState(initial = MesAppSettings()).value

    // Launch the Home UI Decisions
    HomeUiStateDecisions(
        homeUiState = homeUiState,
        mesAppSettings = mesAppSettings,
        retryAction = { homeViewModel.refresh(mesAppSettings.emergencyButtonActionIdentifier) },
        navigateToPreCall = navigateToPreCall,
        scrollState = scrollState,
        modifier = modifier
    )
}

@Composable
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun HomeUiStateDecisions(
    homeUiState: HomeUiState,
    mesAppSettings: MesAppSettings,
    retryAction: () -> Unit,
    navigateToPreCall: (service: Service, chosenNumber: String) -> Unit,
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
) {
    when (homeUiState) {
        is HomeUiState.Success -> {
            HomeContent(
                homeUiState = homeUiState,
                navigateToPreCall = navigateToPreCall,
                mesAppSettings = mesAppSettings,
                modifier = modifier,
                scrollState = scrollState
            )
        }

        is HomeUiState.Loading -> MesScreenAnimatedLoading(
            loadingMessage = stringResource(id = R.string.message_loading_services),
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )

        is HomeUiState.Error ->
            MesScreenError(
                retryAction = retryAction,
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            )

        is HomeUiState.NoNetwork ->
            MesScreenError(
                retryAction = retryAction,
                errorMessageId = R.string.message_internet_connection_needed,
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            )
    }
}

@Composable
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun HomeContent(
    homeUiState: HomeUiState.Success,
    navigateToPreCall: (service: Service, chosenNumber: String) -> Unit,
    mesAppSettings: MesAppSettings,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.headline_home_primary),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 32.dp,
                        bottom = 4.dp
                    )
            )

            Text(
                text = stringResource(id = R.string.headline_home_secondary),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 4.dp,
                        bottom = 8.dp
                    )
            )
        }

        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp)
        ) {
            MesEmergencyButton(
                onClick = {
                    navigateToPreCall(
                        homeUiState.services.first {
                            it.identifier == mesAppSettings.emergencyButtonActionIdentifier
                        },
                        homeUiState.services.first().main_contact.toString()
                    )
                },
                modifier = Modifier
                    .size(200.dp)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {

            Text(
                text = stringResource(id = R.string.headline_home_tertiary),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(8.dp)
            )

            Text(
                text = stringResource(id = R.string.headline_home_quaternary),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(
                        bottom = 16.dp
                    )
            )

            MesEmergencyRow(
                services = homeUiState.services,
                navigateToPreCall = navigateToPreCall
            )
        }

    }
}

@Composable
@ExperimentalMaterial3Api
fun MesEmergencyRow(
    services: List<Service>,
    navigateToPreCall: (service: Service, chosenNumber: String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Create the Lazy row
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp)
    ) {
        // Load filtered services
        items(
            services
        ) { service ->
            MesEmergencyItem(
                service = service,
                onClick = {
                    navigateToPreCall(service, service.main_contact.toString())
                }
            )
        }
    }
}

@Preview("Home Screen Light")
@Preview("Home Screen Dark", uiMode = UI_MODE_NIGHT_YES)
@Preview(
    name = "Home Screen Not Network",
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun ScreenHomePreview() {

    MesTheme {
        HomeUiStateDecisions(
            homeUiState = HomeUiState.Success(DummyData.services),
            mesAppSettings = MesAppSettings(),
            navigateToPreCall = { _, _ -> },
            retryAction = {},
            scrollState = rememberScrollState()
        )
    }
}

@Preview("Home Screen Loading Light")
@Preview("Home Screen Loading Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun HomeLoadingPreview() {

    MesTheme {
        HomeUiStateDecisions(
            homeUiState = HomeUiState.Loading,
            mesAppSettings = MesAppSettings(),
            navigateToPreCall = { _, _ -> },
            retryAction = {},
            scrollState = rememberScrollState()
        )
    }
}

@Preview("Home Screen Error Light")
@Preview("Home Screen Error Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun HomeErrorPreview() {

    MesTheme {
        HomeUiStateDecisions(
            homeUiState = HomeUiState.Error,
            mesAppSettings = MesAppSettings(),
            navigateToPreCall = { _, _ -> },
            retryAction = {},
            scrollState = rememberScrollState()
        )
    }
}

@Preview("Home Screen Not Network Light")
@Preview("Home Screen Not Network Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun HomeNoNetworkPreview() {

    MesTheme {
        HomeUiStateDecisions(
            homeUiState = HomeUiState.NoNetwork,
            mesAppSettings = MesAppSettings(),
            navigateToPreCall = { _, _ -> },
            retryAction = {},
            scrollState = rememberScrollState()
        )
    }
}