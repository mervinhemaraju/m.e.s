package com.th3pl4gu3.mauritius_emergency_services.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Elevations(val superLow: Dp, val extraLow: Dp, val low: Dp, val normal: Dp, val high: Dp)

private val elev = Elevations(superLow = 0.7.dp, extraLow = 2.dp, low = 4.dp, normal = 6.dp, high = 12.dp)

private val LocalElevations = staticCompositionLocalOf {
    elev
}

val elevation: Elevations
    @Composable
    get() = LocalElevations.current
