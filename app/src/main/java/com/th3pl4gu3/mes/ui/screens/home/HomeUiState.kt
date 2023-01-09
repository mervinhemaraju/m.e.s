package com.th3pl4gu3.mes.ui.screens.home

import com.th3pl4gu3.mes.models.MesResponse

sealed interface HomeUiState {
    data class Success(val servicesResponse: MesResponse) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}