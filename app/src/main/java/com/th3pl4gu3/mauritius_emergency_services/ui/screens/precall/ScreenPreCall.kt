package com.th3pl4gu3.mauritius_emergency_services.ui.screens.precall

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CallEnd
import androidx.compose.material.icons.outlined.KeyboardDoubleArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mauritius_emergency_services.MesActivity
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import com.th3pl4gu3.mauritius_emergency_services.ui.components.*
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.Red500
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

private const val TAG = "PRE_CALL_SCREEN"

@Composable
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun ScreenPreCall(
    preCallUiState: PreCallUiState,
    countdown: String?,
    startCall: Boolean,
    closeScreen: () -> Unit,
) {
    // Load the UI
    when (preCallUiState) {
        is PreCallUiState.Success ->
        {
            if(startCall){
                val activity = LocalContext.current as MesActivity
                with(Intent(Intent.ACTION_CALL)){
                    data = Uri.parse("tel:${preCallUiState.service.main_contact}")
                    closeScreen()
                    activity.startActivity(this)
                }
            }

            PreCallContent(
                service = preCallUiState.service,
                countdown = if (countdown.isNullOrEmpty()) stringResource(id = R.string.message_error_loading_countdown) else countdown,
                closeScreen = closeScreen
            )
        }
        is PreCallUiState.Error -> MesScreenError(
            retryAction = closeScreen,
            errorMessage = stringResource(id = R.string.message_error_call_startup_failed),
            errorButtonText = stringResource(id = R.string.action_close)
        )
        is PreCallUiState.Loading -> MesScreenLoading(
            loadingMessage = stringResource(id = R.string.message_loading_call_in_progress)
        )
    }
}

@Composable
@ExperimentalAnimationApi
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
            text = stringResource(id = R.string.headline_pre_call_primary),
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
            color = MaterialTheme.colorScheme.onSurface,
        )


        Text(
            modifier = Modifier
                .wrapContentSize()
                .weight(1f),
            text = service.main_contact.toString(),
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

            AnimatedContent(
                targetState = countdown,
                transitionSpec = {
                    MesCountDownAnimation().using(
                        SizeTransform(clip = false)
                    )
                }
            ) { targetCount ->

                Text(
                    text = targetCount,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = Red500,
                    fontWeight = FontWeight.Bold
                )
            }

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

    val close = SwipeAction(
        onSwipe = {
            closeScreen()
        },
        icon = {
            MesIcon(
                imageVector = Icons.Outlined.CallEnd,
                modifier = Modifier.padding(start = 16.dp),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = R.string.action_call_end
            )
        },
        background = Red500,
    )

    SwipeableActionsBox(
        swipeThreshold = 120.dp,
        endActions = listOf(close),
        backgroundUntilSwipeThreshold = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .height(80.dp)
            .clip(shape)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(shape)
                .background(MaterialTheme.colorScheme.primary)
                .padding(4.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(id = R.string.action_slide_cancel),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                imageVector = Icons.Outlined.KeyboardDoubleArrowLeft,
                contentDescription = stringResource(id = R.string.action_swipe),
                Modifier
                    .fillMaxHeight()
                    .width(48.dp)
                    .clip(RoundedCornerShape(12.dp))
//                    .size(54.dp)
                    .background(MaterialTheme.colorScheme.background)
            )
        }
    }
}

@Preview("PreCall Screen Light", showBackground = true)
@Preview("PreCall Screen Dark", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun PreviewScreenPreCallContent() {

    val mockData = Service(
        identifier = "security-police-direct-1",
        name = "Police Direct Line 1",
        type = "E",
        icon = "https://img.icons8.com/fluent/100/000000/policeman-male.png",
        main_contact = 999,
        emails = listOf(),
        other_contacts = listOf()
    )

    MesTheme {
        PreCallContent(
            service = mockData,
            closeScreen = {},
            countdown = "N",
        )
    }
}

@Preview("PreCall Screen Loading Light", showBackground = true)
@Preview("PreCall Screen Loading Dark", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
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
@ExperimentalAnimationApi
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