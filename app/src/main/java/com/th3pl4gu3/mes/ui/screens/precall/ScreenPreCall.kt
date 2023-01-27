package com.th3pl4gu3.mes.ui.screens.precall

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.th3pl4gu3.mes.models.Service
import com.th3pl4gu3.mes.ui.components.MesAsyncRoundedImage
import com.th3pl4gu3.mes.ui.components.MesIcon
import com.th3pl4gu3.mes.ui.components.MesScreenError
import com.th3pl4gu3.mes.ui.components.MesScreenLoading
import com.th3pl4gu3.mes.ui.theme.MesTheme
import com.th3pl4gu3.mes.ui.theme.Orange500
import com.th3pl4gu3.mes.ui.theme.Red500
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import me.saket.swipe.rememberSwipeableActionsState

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
            countdown = if (countdown.isNullOrEmpty()) "ERROR" else countdown,
            closeScreen = closeScreen
        )
        is PreCallUiState.Error -> MesScreenError(
            retryAction = closeScreen,
            errorMessage = "Unable to perform the phone call at the moment. Please try again.",
            errorButtonText = "Close"
        )
        is PreCallUiState.Loading -> MesScreenLoading(
            loadingMessage = "Phone call in process..."
        )
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            modifier = Modifier
                .wrapContentSize()
                .weight(1f),
            text = "Starting a call to",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier
                .wrapContentSize()
                .weight(1f),
            text = service.name,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )


        Text(
            modifier = Modifier
                .wrapContentSize()
                .weight(1f),
            text = service.number.toString(),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(48.dp))

        MesAsyncRoundedImage(
            service = service,
            size = 160.dp,
            modifier = Modifier
                .border(
                    width = 4.dp,
                    color = Red500,
                    shape = RoundedCornerShape(50)
                )
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )

        Box(
            modifier = Modifier
                .wrapContentSize()
                .weight(10f)
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = countdown,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = Red500,
                fontWeight = FontWeight.Bold
            )

        }

        SwipeToCancel(
            closeScreen = closeScreen,
            modifier = Modifier
        )
    }
}

@Composable
fun SwipeToCancel(
    closeScreen: () -> Unit,
    modifier: Modifier = Modifier
) {

    val shape = MaterialTheme.shapes.medium

    val archive = SwipeAction(
        onSwipe = {
            closeScreen()
        },
        icon = {
            MesIcon(
                imageVector = Icons.Outlined.CallEnd,
                modifier = Modifier.padding(start = 16.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        },
        background = Red500,
    )

    SwipeableActionsBox(
        swipeThreshold = 120.dp,
        endActions = listOf(archive),
        backgroundUntilSwipeThreshold = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .clip(shape)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape)
                .background(MaterialTheme.colorScheme.primary)
                .padding(4.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Slide to Cancel",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                imageVector = Icons.Outlined.KeyboardDoubleArrowLeft,
                contentDescription = null,
                Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.background)
            )
        }
    }
}

@Preview("PreCall Screen Light", showBackground = true)
@Preview("PreCall Screen Dark", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalComposeUiApi
fun PreviewScreenPreCallContent() {

    val mockData = Service(
        identifier = "security-police-direct-1",
        name = "Police Direct Line 1",
        type = "E",
        icon = "https://img.icons8.com/fluent/100/000000/policeman-male.png",
        number = 999
    )

    MesTheme {
        ScreenPreCall(
            preCallUiState = PreCallUiState.Success(mockData),
            closeScreen = {},
            countdown = "N",
            startCall = true,
        )
    }
}

@Preview("PreCall Screen Loading Light", showBackground = true)
@Preview("PreCall Screen Loading Dark", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalComposeUiApi
fun PreviewScreenPreCallLoading() {
    MesTheme {
        ScreenPreCall(
            preCallUiState = PreCallUiState.Loading,
            closeScreen = {},
            countdown = "N",
            startCall = true,
        )
    }
}

@Preview("PreCall Screen Error Light", showBackground = true)
@Preview("PreCall Screen Error Dark", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalComposeUiApi
fun PreviewScreenPreCallError() {
    MesTheme {
        ScreenPreCall(
            preCallUiState = PreCallUiState.Error,
            closeScreen = {},
            countdown = "N",
            startCall = true,
        )
    }
}