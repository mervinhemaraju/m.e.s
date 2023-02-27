package com.th3pl4gu3.mauritius_emergency_services.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@ExperimentalAnimationApi
fun MesCountDownAnimation(duration: Int = 800): ContentTransform {
    return slideInVertically(animationSpec = tween(durationMillis = duration)) { height -> height } + fadeIn(
        animationSpec = tween(durationMillis = duration)
    ) with slideOutVertically(animationSpec = tween(durationMillis = duration)) { height -> -height } + fadeOut(
        animationSpec = tween(durationMillis = duration)
    )
}

@Composable
fun MesAnimatedVisibilityExpandedHorizontallyContent(
    visibility: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val duration = 500
    AnimatedVisibility(
        visible = visibility,
        enter = expandHorizontally(
            animationSpec = tween(durationMillis = duration)
        ),
        exit = shrinkHorizontally(
            animationSpec = tween(durationMillis = duration)
        ),
        modifier = modifier
    ) {
        content()
    }
}

@Composable
fun MesAnimatedVisibilityExpandVerticallyContent(
    visibility: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    AnimatedVisibility(
        visible = visibility,
        enter = expandVertically(),
        exit = shrinkVertically(),
        modifier = modifier
    ) {
        content()
    }
}


@Composable
fun MesAnimatedVisibilitySlideVerticallyContent(
    visibility: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val duration = 500
    AnimatedVisibility(
        visible = visibility,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = duration),
            initialOffsetY = { it / 2 }
        ),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis = duration),
            targetOffsetY = { it/2 }
        ),
        modifier = modifier
    ) {
        content()
    }
}