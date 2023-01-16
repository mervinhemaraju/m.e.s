package com.th3pl4gu3.mes.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.th3pl4gu3.mes.MesApplication
import com.th3pl4gu3.mes.models.Service
import com.th3pl4gu3.mes.ui.components.MesEmergencyButton
import com.th3pl4gu3.mes.ui.components.MesEmergencyItem
import com.th3pl4gu3.mes.ui.theme.MesTheme

@Composable
@ExperimentalMaterial3Api
fun ScreenHome(
    viewModel: HomeViewModel,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {

    val homeUiState: HomeUiState by viewModel.homeUiState.collectAsState()

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

        when(homeUiState) {
            is HomeUiState.Success -> {
                MesEmergencyRow(
                    services = (homeUiState as HomeUiState.Success).services
                )
            }
            is HomeUiState.Loading -> LoadingScreen(modifier)
            is HomeUiState.Error -> ErrorScreen(retryAction, modifier.padding(bottom = 16.dp))
        }
    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
//    Column(
//        modifier = modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("Loading...")
//    }

    Text(
        text = "Loading...",
        modifier = modifier
    )
    
    Spacer(modifier = Modifier.height(24.dp))
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
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

    val homeViewModel: HomeViewModel = viewModel(
        factory = HomeViewModel.provideFactory(appContainer = MesApplication().container)
    )

    MesTheme {
        ScreenHome(
            viewModel = homeViewModel,
            retryAction = {}
        )
    }
}