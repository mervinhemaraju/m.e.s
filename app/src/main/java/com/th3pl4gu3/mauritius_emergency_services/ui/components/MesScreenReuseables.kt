package com.th3pl4gu3.mauritius_emergency_services.ui.components

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme

@Composable
fun MesScreenLinearLoading(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LinearProgressIndicator(
            trackColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun MesScreenAnimatedLoading(
    loadingMessage: String,
    modifier: Modifier = Modifier,
    iconSize: Dp = 48.dp,
    animationDurationMillis: Int = 400
) {
    // Animate for the scale of each icon
    val scale1 = remember { Animatable(1f) }
    val scale2 = remember { Animatable(1f) }
    val scale3 = remember { Animatable(1f) }

    // Trigger the animation
    LaunchedEffect(Unit) {
        while (true) {
            // Sequential pulsing
            scale1.animateTo(1.5f, animationSpec = tween(animationDurationMillis)) // Scale up
            scale1.animateTo(1f, animationSpec = tween(animationDurationMillis))   // Scale back

            scale2.animateTo(1.5f, animationSpec = tween(animationDurationMillis))
            scale2.animateTo(1f, animationSpec = tween(animationDurationMillis))

            scale3.animateTo(1.5f, animationSpec = tween(animationDurationMillis))
            scale3.animateTo(1f, animationSpec = tween(animationDurationMillis))
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.align(Alignment.CenterHorizontally)
        ) {
            MesIcon(
                imageVector = Icons.Filled.Circle,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(iconSize * scale1.value) // Apply animated scale
            )
            MesIcon(
                imageVector = Icons.Filled.Circle,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .size(iconSize * scale2.value) // Apply animated scale
            )
        }
        MesIcon(
            imageVector = Icons.Filled.Circle,
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(iconSize * scale3.value) // Apply animated scale
        )

        Spacer(modifier = Modifier.size(21.dp))

        Text(
            text = loadingMessage,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun MesScreenError(
    modifier: Modifier = Modifier,
    retryAction: () -> Unit,
    tint: Color = MaterialTheme.colorScheme.primary,
    imageId: Int = R.drawable.ic_island,
    errorMessageId: Int = R.string.message_error_content_failed,
    errorButtonText: String = stringResource(id = R.string.action_retry)
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        MesIcon(
            painterResource = imageId,
            contentDescription = errorMessageId,
            tint = tint,
        )

        Text(
            text = stringResource(errorMessageId),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .size(32.dp))

        MesTextButton(
            text = errorButtonText,
            onClick = retryAction,
        )
    }
}

@Composable
fun MesScreenNoContent(
    modifier: Modifier = Modifier,
    messageId: Int,
    tint: Color = MaterialTheme.colorScheme.primary
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MesIcon(
            painterResource = R.drawable.ic_no_data,
            contentDescription = messageId,
            tint = tint
        )

        Spacer(
            modifier = Modifier.size(21.dp)
        )

        Text(
            text = stringResource(messageId),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

/**
 * Screen Previews
 **/
@Composable
@Preview("Animated Loading Light Preview", showBackground = true)
@Preview(
    "Animated Loading Dark Preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun MesScreenAnimatedLoadingPreview() {
    MesTheme {
        MesScreenAnimatedLoading(
            loadingMessage = "Loading...",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
@Preview("Linear Loading Light Preview", showBackground = true)
@Preview(
    "Linear Loading Dark Preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun MesScreenLinearLoadingPreview() {
    MesTheme {
        MesScreenLinearLoading(
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview("Error Light Preview", showBackground = true)
@Preview(
    "Error Dark Preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun MesScreenErrorPreview() {
    MesTheme {
        MesScreenError(
            retryAction = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Preview("No Content Light Preview", showBackground = true)
@Preview(
    "No Content Dark Preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun MesScreenNoContentPreview() {
    MesTheme {
        MesScreenNoContent(
            messageId = R.string.message_services_not_found,
            modifier = Modifier.fillMaxSize()
        )
    }
}