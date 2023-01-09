package com.th3pl4gu3.mes.ui.screens.services

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.data.DummyData
import com.th3pl4gu3.mes.models.MesResponse
import com.th3pl4gu3.mes.models.Service
import com.th3pl4gu3.mes.ui.components.MesServiceItem
import com.th3pl4gu3.mes.ui.theme.MesTheme

@Composable
fun ScreenServices(
    servicesUiState: ServicesUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (servicesUiState) {
        is ServicesUiState.Loading -> LoadingScreen(modifier)
        is ServicesUiState.Success -> ServicesList(servicesUiState.servicesResponse, modifier)
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
fun ServicesList(servicesResponse: MesResponse, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ){
        // Load the services
        items(servicesResponse.services) { service ->
            MesServiceItem(
                service = service,
                onClick = {}
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