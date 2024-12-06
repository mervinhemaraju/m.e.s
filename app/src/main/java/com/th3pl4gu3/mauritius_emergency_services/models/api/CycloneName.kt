package com.th3pl4gu3.mauritius_emergency_services.models.api

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CycloneName(
    val name: String,
    val gender: String,
    val provided_by: String,
    val named_by: String
)
