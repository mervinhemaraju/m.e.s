package com.th3pl4gu3.mes.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


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
    val duration = 500
    AnimatedVisibility(
        visible = visibility,
        enter = expandVertically(),
        exit = shrinkVertically(),
        modifier = modifier
    ) {
        content()
    }
}
