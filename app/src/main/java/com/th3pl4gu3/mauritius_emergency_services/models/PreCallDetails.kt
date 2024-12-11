package com.th3pl4gu3.mauritius_emergency_services.models

data class PreCallDetails(
    var serviceIdentifier: String = "",
    var chosenNumber: String = "",
    var isEmergency: Boolean = false
)