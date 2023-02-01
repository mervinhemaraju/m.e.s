package com.th3pl4gu3.mauritius_emergency_services.ui.screens.precall

import com.th3pl4gu3.mauritius_emergency_services.models.Service

sealed interface PreCallUiState {
    data class Success(val service: Service) : PreCallUiState
    object Error : PreCallUiState
    object Loading : PreCallUiState
}