package com.th3pl4gu3.mauritius_emergency_services.ui.screens.services

import com.th3pl4gu3.mauritius_emergency_services.models.Service

sealed interface ServicesUiState {
    data class Success(val services: List<Service>) : ServicesUiState
    object Error : ServicesUiState
    object Loading : ServicesUiState
    object NoContent: ServicesUiState
    object NoNetwork: ServicesUiState
}