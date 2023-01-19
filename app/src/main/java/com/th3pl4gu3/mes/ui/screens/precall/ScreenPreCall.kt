package com.th3pl4gu3.mes.ui.screens.precall

import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.th3pl4gu3.mes.MainActivity
import com.th3pl4gu3.mes.models.Service
import com.th3pl4gu3.mes.ui.components.MesAsyncRoundedImage
import com.th3pl4gu3.mes.ui.theme.MesTheme
import com.th3pl4gu3.mes.ui.theme.Orange500
import com.th3pl4gu3.mes.ui.theme.Red50
import com.th3pl4gu3.mes.ui.theme.Red500

@Composable
@ExperimentalComposeUiApi
fun ScreenPreCall(
    preCallUiState: PreCallUiState,
    countdown: String?,
    startCall: Boolean?,
    closeScreen: () -> Unit,
) {
    // Load the UI
    when (preCallUiState) {
        is PreCallUiState.Success -> PreCallContent(
            service = preCallUiState.service,
            countdown = if(countdown.isNullOrEmpty()) "ERROR" else countdown,
            closeScreen = closeScreen
        )
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
fun PreCallContent(
    service: Service,
    countdown: String,
    closeScreen: () -> Unit,
) {

    // Log for information
    Log.i(
        "pre_call_service",
        "Service obtained on pre-call. service_identifier: ${service.identifier}"
    )

    /**
     * Change the System Bar colors to match
     * this new design color
     **/
    with(rememberSystemUiController()){

        setStatusBarColor(
            Orange500
        )

        setNavigationBarColor(
            Red500
        )
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Orange500,
                        Red500
                    ),
                    tileMode = TileMode.Clamp
                )
            )
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
            color = Color.White
        )

        Text(
            modifier = Modifier.constrainAs(textHeader) {
                top.linkTo(textCaption.bottom, margin = 32.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
            },
            text = service.name,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            color = Color.White
        )

        Text(
            modifier = Modifier.constrainAs(textEmergencyNumber) {
                top.linkTo(textHeader.bottom, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
            },
            text = service.number.toString(),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = Color.White
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
                    width = 4.dp,
                    color = Red500,
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
            text = countdown,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = Color.White
        )

        FloatingActionButton(
            onClick = {
                closeScreen()
            },
            modifier = Modifier
                .constrainAs(buttonCancel) {
                    bottom.linkTo(parent.bottom, margin = 48.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .size(80.dp),
            containerColor = Color.Black
        ) {
            Icon(imageVector = Icons.Outlined.Close, contentDescription = "Cancel button", tint = Color.White)
        }
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
        PreCallContent(service = mockData, closeScreen = {}, countdown = "N")
    }
}