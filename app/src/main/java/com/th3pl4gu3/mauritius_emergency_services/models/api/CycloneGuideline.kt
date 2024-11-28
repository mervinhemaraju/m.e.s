package com.th3pl4gu3.mauritius_emergency_services.models.api

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CycloneGuideline(
    val level: String,
    val description: String,
    val precautions: List<String>
)
