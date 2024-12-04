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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CallEnd
import androidx.compose.material.icons.outlined.KeyboardDoubleArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.sp
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.activity.MesActivity
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesAsyncRoundedImage
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesCountDownAnimation
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesIcon
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesScreenAnimatedLoading
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesScreenError
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun ScreenPreCall(
    preCallViewModel: PreCallViewModel,
    closeScreen: () -> Unit,
) {
    /** Get the UI State from the view model **/
    val preCallUiState by preCallViewModel.service.collectAsState()

    /** Get the countdown from the view model **/
    val countdown by preCallViewModel.seconds.collectAsState(initial = 5)

    /** Get the start call flag from the view model **/
    val startCall: Boolean by preCallViewModel.startCall.collectAsState()

    /** Launch the UI State decisions **/
    PreCallUiStateDecisions(
        preCallUiState = preCallUiState,
        countdown = countdown.toString(),
        startCall = startCall,
        closeScreen = closeScreen
    )
}

@Composable
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun PreCallUiStateDecisions(
    preCallUiState: PreCallUiState,
    countdown: String?,
    startCall: Boolean,
    closeScreen: () -> Unit,
){
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
            errorMessageId = R.string.message_error_call_startup_failed,
            errorButtonText = stringResource(id = R.string.action_close),
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )
        is PreCallUiState.Loading -> MesScreenAnimatedLoading(
            loadingMessage = stringResource(id = R.string.message_loading_call_in_progress),
            modifier = Modifier.fillMaxSize()
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
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier
                .wrapContentSize()
                .weight(1f),
            text = service.name,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.secondary,
        )


        Text(
            modifier = Modifier
                .wrapContentSize()
                .weight(2f),
            text = service.main_contact.toString(),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.height(48.dp))

        MesAsyncRoundedImage(
            service = service,
            size = 160.dp,
            modifier = Modifier
                .border(
                    width = 4.dp,
                    color = MaterialTheme.colorScheme.tertiary,
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
                }, label = ""
            ) { targetCount ->

                Text(
                    text = targetCount,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 48.sp
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

    // FIXME(Implement this functionality using pure jetpack compose)
    val shape = MaterialTheme.shapes.medium

    val close = SwipeAction(
        onSwipe = {
            closeScreen()
        },
        icon = {
            MesIcon(
                imageVector = Icons.Outlined.CallEnd,
                modifier = Modifier.padding(start = 16.dp),
                tint = MaterialTheme.colorScheme.onError,
                contentDescription = stringResource(R.string.action_call_end)
            )
        },
        background = MaterialTheme.colorScheme.error,
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
                contentDescription = stringResource(id = R.string.action_swipe_cancel),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(48.dp)
                    .clip(RoundedCornerShape(12.dp))
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
        PreCallUiStateDecisions(
            preCallUiState = PreCallUiState.Success(mockData),
            closeScreen = {},
            countdown = "N",
            startCall = false
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
        PreCallUiStateDecisions(
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
        PreCallUiStateDecisions(
            preCallUiState = PreCallUiState.Error,
            closeScreen = {},
            countdown = "N",
            startCall = true,
        )
    }
}