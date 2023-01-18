package com.th3pl4gu3.mes.ui.screens.services

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.th3pl4gu3.mes.models.Service
import com.th3pl4gu3.mes.ui.components.MesServiceItem

@Composable
fun ScreenServices(
    servicesUiState: ServicesUiState,
    retryAction: () -> Unit,
    navigateToPreCall: (service: Service) -> Unit,
    modifier: Modifier = Modifier
) {
    when (servicesUiState) {
        is ServicesUiState.Loading -> LoadingScreen(modifier)
        is ServicesUiState.Success -> ServicesList((servicesUiState as ServicesUiState.Success).services, navigateToPreCall, modifier)
        is ServicesUiState.Error -> ErrorScreen(retryAction, modifier)
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
fun ServicesList(
    services: List<Service>,
    navigateToPreCall: (service: Service) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    ){
        // Load the services
        items(services) { service ->
            MesServiceItem(
                service = service,
                onClick = {
                    Log.i("pre_call_service", "Launching PreCall with service identifier: ${service.identifier}")

                    navigateToPreCall(service)
                }
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LoadingScreenPreview() {
//    MesTheme {
//        LoadingScreen()
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ErrorScreenPreview() {
//    MesTheme {
//        ErrorScreen({})
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PhotosGridScreenPreview() {
//    MesTheme {
//        val mockData = List(10) { MarsPhoto("$it", "") }
//        ServicesList(mockData)
//    }
//}