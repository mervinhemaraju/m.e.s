package com.th3pl4gu3.mauritius_emergency_services.models.responses

import androidx.annotation.Keep
import com.th3pl4gu3.mauritius_emergency_services.models.api.CycloneReport
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class MesCycloneReportResponse(
    val report: CycloneReport,
    val message: String,
    val success: Boolean
)