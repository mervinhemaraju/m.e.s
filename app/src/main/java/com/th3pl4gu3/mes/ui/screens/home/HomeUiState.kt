package com.th3pl4gu3.mes.ui.screens.home

import com.th3pl4gu3.mes.models.Service

sealed interface HomeUiState {
    data class Success(val services: List<Service>) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}

//data class HomeUiState(val services: List<Service> = listOf())