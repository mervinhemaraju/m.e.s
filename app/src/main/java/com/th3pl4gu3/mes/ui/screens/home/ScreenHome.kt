package com.th3pl4gu3.mes.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.th3pl4gu3.mes.data.DummyData
import com.th3pl4gu3.mes.models.MesAppSettings
import com.th3pl4gu3.mes.models.Service
import com.th3pl4gu3.mes.ui.components.MesEmergencyButton
import com.th3pl4gu3.mes.ui.components.MesEmergencyItem
import com.th3pl4gu3.mes.ui.components.MesTextButton
import com.th3pl4gu3.mes.ui.theme.MesTheme


@Composable
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun ScreenHome(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    navigateToPreCall: (service: Service) -> Unit,
    mesAppSettings: MesAppSettings,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                top = 16.dp,
                bottom = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Emergency Police Help Needed ?",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
        )

        Text(
            text = "Click the button to quick call",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
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
            text = "Need other quick emergency actions?",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
        )

        Text(
            text = "Click one below to call",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(
                    bottom = 16.dp
                )
        )

        DynamicContent(
            homeUiState = homeUiState,
            navigateToPreCall = navigateToPreCall,
            retryAction = retryAction,
            modifier = Modifier
        )
    }
}

@Composable
@ExperimentalMaterial3Api
fun DynamicContent(
    homeUiState: HomeUiState,
    navigateToPreCall: (service: Service) -> Unit,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (homeUiState) {
        is HomeUiState.Success -> {
            MesEmergencyRow(
                services = homeUiState.services,
                navigateToPreCall = navigateToPreCall,
                modifier = modifier
            )
        }
        is HomeUiState.Loading -> LoadingScreen(modifier)
        is HomeUiState.Error -> ErrorScreen(retryAction, modifier)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {

        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 8.dp,
            modifier = Modifier
                .size(54.dp)
                .padding(16.dp)
        )

        Text(
            text = "Getting services...",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {

        Text(
            text = "Loading failed",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(8.dp)
        )

        MesTextButton(text = "Retry", onClick = retryAction)
    }
}

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