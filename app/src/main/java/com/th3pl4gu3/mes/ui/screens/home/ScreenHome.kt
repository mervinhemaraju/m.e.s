package com.th3pl4gu3.mes.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.data.DummyData
import com.th3pl4gu3.mes.models.MesResponse
import com.th3pl4gu3.mes.models.Service
import com.th3pl4gu3.mes.ui.components.MesEmergencyButton
import com.th3pl4gu3.mes.ui.components.MesEmergencyItem
import com.th3pl4gu3.mes.ui.components.MesIcon
import com.th3pl4gu3.mes.ui.components.MesServiceItem
import com.th3pl4gu3.mes.ui.screens.services.ServicesUiState
import com.th3pl4gu3.mes.ui.theme.MesTheme

@Composable
@ExperimentalMaterial3Api
fun ScreenHome(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (homeUiState) {
        is HomeUiState.Loading -> LoadingScreen(modifier)
        is HomeUiState.Success -> EmergencyServicesList(homeUiState.servicesResponse, modifier)
        is HomeUiState.Error -> ErrorScreen(retryAction, modifier)
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
        Text("Loading...")
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
        Text("Loading failed")
        Button(onClick = retryAction) {
            Text("Retry")
        }
    }
}

/**
 * The home screen displaying photo grid.
 */
@Composable
@ExperimentalMaterial3Api
fun EmergencyServicesList(servicesResponse: MesResponse, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Emergency Police Help Needed ?",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = "Hold the button to quick call",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                bottom = 16.dp,
                start = 16.dp,
                end = 16.dp
            )
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        MesEmergencyButton(
            onClick = {},
            modifier = Modifier
                .size(200.dp)
                .padding(8.dp)
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "Need other quick emergency actions?",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = "Click one below to call",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                bottom = 16.dp,
                start = 16.dp,
                end = 16.dp
            )
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        MesEmergencyRow(
            services = servicesResponse.services
        )
    }
}

@Composable
@ExperimentalMaterial3Api
fun MesEmergencyRow(
    services: List<Service>
){
    // Create the Lazy row
    LazyRow {
        // Load the services
        items(services) { service ->
            MesEmergencyItem(
                service = service,
                onClick = {}
            )
        }
    }
}

@Preview("Home Screen Light")
@Preview("Home Screen Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
fun ScreenHomePreview() {

    val mockData = MesResponse(
        services = DummyData.services,
        message = "Success",
        success = true
    )

    MesTheme {
        ScreenHome(
            homeUiState = HomeUiState.Success(servicesResponse = mockData),
            retryAction = {}
        )
    }
}