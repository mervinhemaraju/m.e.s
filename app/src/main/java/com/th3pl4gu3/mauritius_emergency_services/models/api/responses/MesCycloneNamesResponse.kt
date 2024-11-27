package com.th3pl4gu3.mauritius_emergency_services.models.responses

import androidx.annotation.Keep
import com.th3pl4gu3.mauritius_emergency_services.models.api.CycloneName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class MesCycloneNamesResponse(
    val names: List<CycloneName>,
    val message: String,
    val success: Boolean
)