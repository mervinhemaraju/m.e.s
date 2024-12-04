package com.th3pl4gu3.mauritius_emergency_services.ui.screens.home

import com.th3pl4gu3.mauritius_emergency_services.models.Service

sealed interface HomeUiState {
    data class Success(val services: List<Service>) : HomeUiState
    data object Error : HomeUiState
    data object Loading : HomeUiState
    data object NoNetwork: HomeUiState
}