package com.th3pl4gu3.mauritius_emergency_services.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CycloneReport(
    @SerializedName("class") val class_level: Int,
    val next_bulletin: String,
    val news: List<String>
)
