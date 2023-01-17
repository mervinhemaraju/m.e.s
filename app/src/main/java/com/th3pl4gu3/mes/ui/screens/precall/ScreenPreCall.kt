package com.th3pl4gu3.mes.ui.screens.precall

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.th3pl4gu3.mes.models.Service
import com.th3pl4gu3.mes.ui.components.MesAsyncRoundedImage
import com.th3pl4gu3.mes.ui.theme.MesTheme

@Composable
@ExperimentalComposeUiApi
fun ScreenPreCall(
    viewModel: PreCallViewModel
) {

    // Get service
    val preCallUiState: PreCallUiState by viewModel.service.collectAsState()

    // Load the UI
    when (preCallUiState) {
        is PreCallUiState.Success -> PreCallContent(service = (preCallUiState as PreCallUiState.Success).service)
        is PreCallUiState.Error -> PreCallError()
        is PreCallUiState.Loading -> PreCallLoading()
    }
}

@Composable
fun PreCallLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Loading...")
    }
}

@Composable
fun PreCallError(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Loading failed. Please go back")
    }
}

@Composable
fun PreCallContent(service: Service) {

    // Log for information
    Log.i(
        "pre_call_service",
        "Service obtained on pre-call. service_identifier: ${service.identifier}"
    )

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {

        val (
            textCaption,
            textHeader,
            textEmergencyNumber,
            textCountdown,
            emergencyIcon,
            buttonCancel
        ) = createRefs()

        Text(
            modifier = Modifier.constrainAs(textCaption) {
                top.linkTo(parent.top, margin = 32.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
            },
            text = "Starting a call to",
            style = MaterialTheme.typography.labelSmall,
        )

        Text(
            modifier = Modifier.constrainAs(textHeader) {
                top.linkTo(textCaption.bottom, margin = 32.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
            },
            text = service.name,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.constrainAs(textEmergencyNumber) {
                top.linkTo(textHeader.bottom, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
            },
            text = service.number.toString(),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        MesAsyncRoundedImage(
            service = service,
            size = 160.dp,
            modifier = Modifier
                .constrainAs(emergencyIcon) {
                    top.linkTo(textEmergencyNumber.bottom, margin = 32.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(50)
                )
                .padding(8.dp)
        )

        Text(
            modifier = Modifier.constrainAs(textCountdown) {
                top.linkTo(emergencyIcon.bottom, margin = 16.dp)
                bottom.linkTo(buttonCancel.top, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
            },
            text = "N",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        FloatingActionButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .constrainAs(buttonCancel) {
                    bottom.linkTo(parent.bottom, margin = 48.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .size(80.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(imageVector = Icons.Outlined.Close, contentDescription = "Cancel button")
        }

//        Button(
//            onClick = { /*TODO*/ },
//            modifier = Modifier.constrainAs(buttonCancel) {
//                bottom.linkTo(parent.bottom, margin = 48.dp)
//                start.linkTo(parent.start, margin = 16.dp)
//                end.linkTo(parent.end, margin = 16.dp)
//            }
//        ) {
//            Icon(imageVector = Icons.Outlined.Cancel, contentDescription = "Cancel button")
//        }
    }
}

@Preview("PreCall Screen Light", showBackground = true)
@Preview("PreCall Screen Dark", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalComposeUiApi
fun PreviewScreenPreCall() {

    val mockData = Service(
        identifier = "security-police-direct-1",
        name = "Police Direct Line 1",
        type = "E",
        icon = "https://img.icons8.com/fluent/100/000000/policeman-male.png",
        number = 999
    )

    MesTheme {
        PreCallContent(service = mockData)
    }
}