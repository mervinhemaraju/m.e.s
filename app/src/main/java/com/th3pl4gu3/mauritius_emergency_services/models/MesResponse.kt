package com.th3pl4gu3.mauritius_emergency_services.models

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class MesResponse(
    val services: List<Service>,
    val message: String,
    val success: Boolean
)