package com.th3pl4gu3.mauritius_emergency_services.ui.screens.services

import com.th3pl4gu3.mauritius_emergency_services.models.Service

sealed interface ServicesUiState {
    data class Success(val services: List<Service>) : ServicesUiState
    data object Error : ServicesUiState
    data object Loading : ServicesUiState
    data object NoContent: ServicesUiState
    data object NoNetwork: ServicesUiState
}