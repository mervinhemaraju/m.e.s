package com.th3pl4gu3.mauritius_emergency_services.ui.screens.cyclone_report

import com.th3pl4gu3.mauritius_emergency_services.models.api.CycloneName

sealed interface CycloneNamesUiState {
    data class Success(val names: List<CycloneName>) : CycloneNamesUiState
    data object Error : CycloneNamesUiState
    data object Loading : CycloneNamesUiState
}