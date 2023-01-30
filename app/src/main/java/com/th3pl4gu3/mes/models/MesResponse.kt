package com.th3pl4gu3.mes.models

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class MesResponse(
    var services: List<Service>,
    var message: String,
    var success: Boolean
)