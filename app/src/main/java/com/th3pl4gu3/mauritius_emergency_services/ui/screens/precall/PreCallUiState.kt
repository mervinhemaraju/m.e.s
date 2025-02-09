package com.th3pl4gu3.mauritius_emergency_services.ui.screens.precall

import com.th3pl4gu3.mauritius_emergency_services.models.Service

sealed interface PreCallUiState {
    data class Success(val service: Service, val isEmergency: Boolean) : PreCallUiState
    data object Error : PreCallUiState
    data object Loading : PreCallUiState
}