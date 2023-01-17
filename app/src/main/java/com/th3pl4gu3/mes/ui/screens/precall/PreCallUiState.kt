package com.th3pl4gu3.mes.ui.screens.precall

import com.th3pl4gu3.mes.models.Service

sealed interface PreCallUiState {
    data class Success(val service: Service) : PreCallUiState
    object Error : PreCallUiState
    object Loading : PreCallUiState
}