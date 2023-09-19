package com.th3pl4gu3.mauritius_emergency_services.models.responses

import androidx.annotation.Keep
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class MesServicesResponse(
    val services: List<Service>,
    val message: String,
    val success: Boolean
)