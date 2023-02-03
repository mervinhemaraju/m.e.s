package com.th3pl4gu3.mauritius_emergency_services.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.data.DummyData
import com.th3pl4gu3.mauritius_emergency_services.models.MesAppSettings
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import com.th3pl4gu3.mauritius_emergency_services.ui.components.*
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme

@Composable
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun ScreenHome(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    navigateToPreCall: (service: Service) -> Unit,
    mesAppSettings: MesAppSettings,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
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
        is HomeUiState.Loading -> MesScreenLoading(
            loadingMessage = stringResource(id = R.string.message_loading_services),
            modifier = modifier
        )
        is HomeUiState.Error ->
            MesScreenError(
                retryAction = retryAction,
                modifier = modifier
            )
        is HomeUiState.NoNetwork ->
            MesScreenError(
                retryAction = retryAction,
                image = painterResource(id = R.drawable.il_no_network),
                errorMessage = stringResource(id = R.string.message_internet_connection_needed),
                modifier = modifier
            )
    }
}

@Composable
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun HomeContent(
    homeUiState: HomeUiState.Success,
    navigateToPreCall: (service: Service) -> Unit,
    mesAppSettings: MesAppSettings,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                top = 16.dp,
                bottom = 16.dp
            )
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = stringResource(id = R.string.headline_home_primary),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
        )

        Text(
            text = stringResource(id = R.string.headline_home_secondary),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
        )

        Box(
            modifier = Modifier
                .wrapContentSize()
                .weight(10f)
                .padding(8.dp)
        ) {
            MesEmergencyButton(
                onClick = {
                    when (homeUiState) {
                        is HomeUiState.Success -> {
                            // Launch the pre-call screen
                            navigateToPreCall(
                                homeUiState.services.first {
                                    it.identifier == mesAppSettings.emergencyButtonActionIdentifier
                                }
                            )
                        }
                        else -> {}
                    }
                },
                modifier = Modifier
                    .size(200.dp)
            )
        }

        Text(
            text = stringResource(id = R.string.headline_home_tertiary),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
        )

        Text(
            text = stringResource(id = R.string.headline_home_quaternary),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
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

//@Composable
//fun LoadingScreen(modifier: Modifier = Modifier) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//        modifier = modifier
//    ) {
//
//        CircularProgressIndicator(
//            color = MaterialTheme.colorScheme.primary,
//            strokeWidth = 8.dp,
//            modifier = Modifier
//                .size(54.dp)
//                .padding(16.dp)
//        )
//
//        Text(
//            text = stringResource(id = R.string.message_loading_services),
//            style = MaterialTheme.typography.bodyLarge,
//            color = MaterialTheme.colorScheme.onSurface,
//            modifier = Modifier
//                .padding(16.dp)
//        )
//    }
//}

//@Composable
//fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//        modifier = modifier
//    ) {
//
//        Text(
//            text = stringResource(id = R.string.message_error_loading_failed),
//            style = MaterialTheme.typography.bodyMedium,
//            color = MaterialTheme.colorScheme.onBackground,
//            modifier = Modifier.padding(8.dp)
//        )
//
//        MesTextButton(
//            text = stringResource(id = R.string.action_retry),
//            onClick = retryAction
//        )
//    }
//}

@Composable
@ExperimentalMaterial3Api
fun MesEmergencyRow(
    services: List<Service>,
    navigateToPreCall: (service: Service) -> Unit,
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
                    navigateToPreCall(service)
                }
            )
        }
    }
}

@Preview("Home Screen Light")
@Preview("Home Screen Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun ScreenHomePreview() {

    MesTheme {
        ScreenHome(
            retryAction = {},
            navigateToPreCall = {},
            homeUiState = HomeUiState.Success(DummyData.services),
            mesAppSettings = MesAppSettings()
        )
    }
}

@Preview("Home Screen Light")
@Preview("Home Screen Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun HomeLoadingPreview() {

    MesTheme {
        ScreenHome(
            retryAction = {},
            navigateToPreCall = {},
            homeUiState = HomeUiState.Loading,
            mesAppSettings = MesAppSettings()
        )
    }
}

@Preview("Home Screen Light")
@Preview("Home Screen Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun HomeErrorPreview() {

    MesTheme {
        ScreenHome(
            retryAction = {},
            navigateToPreCall = {},
            homeUiState = HomeUiState.Error,
            mesAppSettings = MesAppSettings()
        )
    }
}