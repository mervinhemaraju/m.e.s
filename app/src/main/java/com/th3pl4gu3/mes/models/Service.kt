package com.th3pl4gu3.mes.models

import kotlinx.serialization.Serializable

@Serializable
data class Service(
    val identifier: String,
    val name: String,
    val type: String,
    val icon: String,
    val number: Int
)