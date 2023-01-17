package com.th3pl4gu3.mes.ui.screens.services

import com.th3pl4gu3.mes.models.Service

sealed interface ServicesUiState {
    data class Success(val services: List<Service>) : ServicesUiState
    object Error : ServicesUiState
    object Loading : ServicesUiState
}