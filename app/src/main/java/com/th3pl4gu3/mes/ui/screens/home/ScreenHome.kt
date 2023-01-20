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
import com.th3pl4gu3.mes.models.Service
import com.th3pl4gu3.mes.ui.components.MesEmergencyButton
import com.th3pl4gu3.mes.ui.components.MesEmergencyItem
import com.th3pl4gu3.mes.ui.theme.MesTheme

@Composable
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun ScreenHome(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    navigateToPreCall: (service: Service) -> Unit,
    modifier: Modifier = Modifier
) {

    ConstraintLayout(
        modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
    ) {

        val (
            textMainTitle,
            textMainSubtitle,
            textHeaderTitle,
            textHeaderSubtitle,
            buttonEmergency,
            contentDynamic
        ) = createRefs()

        Text(
            text = "Emergency Police Help Needed ?",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(textMainTitle) {
                    with(16.dp) {
                        top.linkTo(parent.top, this)
                        start.linkTo(parent.start, this)
                        end.linkTo(parent.end, this)
                    }
                }
                .fillMaxWidth()
        )

        Text(
            text = "Click the button to quick call",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(textMainSubtitle) {
                    top.linkTo(textMainTitle.bottom, 8.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                }
        )

        MesEmergencyButton(
            onClick = {
                when (homeUiState) {
                    is HomeUiState.Success -> {
                        // Launch the pre-call screen
                        navigateToPreCall(
                            homeUiState.services.first {
                                it.identifier == "security-police-direct-1"
                            }
                        )
                    }
                    else -> {}
                }
            },
            modifier = Modifier
                .size(200.dp)
                .constrainAs(buttonEmergency) {
                    with(16.dp) {
                        top.linkTo(textMainSubtitle.bottom, this)
                        start.linkTo(parent.start, this)
                        end.linkTo(parent.end, this)
                        bottom.linkTo(textHeaderTitle.top, this)
                    }
                }
        )

        Text(
            text = "Need other quick emergency actions?",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(textHeaderTitle) {
                    bottom.linkTo(textHeaderSubtitle.top, 8.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                }
        )

        Text(
            text = "Click one below to call",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(textHeaderSubtitle) {
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(contentDynamic.top, 24.dp)
                }
        )

        DynamicContent(
            homeUiState = homeUiState,
            navigateToPreCall = navigateToPreCall,
            retryAction = retryAction,
            modifier = Modifier
                .constrainAs(contentDynamic) {
                    bottom.linkTo(parent.bottom, 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
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
        Text(
            text = "Loading...",
            modifier = modifier
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
            modifier = modifier
        )

        Button(
            onClick = retryAction,
            modifier = modifier
        ) {
            Text("Retry")
        }
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
            services.filterNot { it.identifier == "security-police-direct-1" }
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
            homeUiState = HomeUiState.Loading
        )
    }
}