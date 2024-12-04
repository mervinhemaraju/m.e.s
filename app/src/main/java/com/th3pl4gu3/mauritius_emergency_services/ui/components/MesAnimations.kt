package com.th3pl4gu3.mauritius_emergency_services.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween


@ExperimentalAnimationApi
fun mesCountDownAnimation(duration: Int = 800): ContentTransform {
    return (slideInVertically(animationSpec = tween(durationMillis = duration)) { height -> height } + fadeIn(
        animationSpec = tween(durationMillis = duration)
    )).togetherWith(slideOutVertically(animationSpec = tween(durationMillis = duration)) { height -> -height } + fadeOut(
        animationSpec = tween(durationMillis = duration)
    ))
}