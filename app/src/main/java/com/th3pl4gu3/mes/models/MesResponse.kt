package com.th3pl4gu3.mes.models

@kotlinx.serialization.Serializable
data class MesResponse(
    val services: List<Service>,
    val message: String,
    val success: Boolean
)