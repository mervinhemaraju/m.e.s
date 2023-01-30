package com.th3pl4gu3.mes.ui.components

import androidx.compose.animation.*
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
fun MesAnimatedVisibilitySlideHorizontallyContent(
    visibility: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val duration = 500
    AnimatedVisibility(
        visible = visibility,
        enter = slideInHorizontally(
            animationSpec = tween(durationMillis = duration),
            initialOffsetX = { it / 2 }
        ),
        exit = slideOutHorizontally(
            animationSpec = tween(durationMillis = duration),
            targetOffsetX = { it / 2}
        ),
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
    AnimatedVisibility(
        visible = visibility,
        enter = expandVertically(),
        exit = shrinkVertically(),
        modifier = modifier
    ) {
        content()
    }
}
