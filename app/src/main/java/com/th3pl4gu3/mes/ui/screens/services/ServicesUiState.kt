package com.th3pl4gu3.mes.ui.screens.services

import com.th3pl4gu3.mes.models.MesResponse

sealed interface ServicesUiState {
    data class Success(val servicesResponse: MesResponse) : ServicesUiState
    object Error : ServicesUiState
    object Loading : ServicesUiState
}