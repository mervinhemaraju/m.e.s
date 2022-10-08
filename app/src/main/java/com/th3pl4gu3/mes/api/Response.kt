package com.th3pl4gu3.mes.api

data class Response(
    val services: List<Service>,
    val message: String,
    val success: Boolean
)