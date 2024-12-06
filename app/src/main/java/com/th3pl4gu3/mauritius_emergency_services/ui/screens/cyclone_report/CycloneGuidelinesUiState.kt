package com.th3pl4gu3.mauritius_emergency_services.ui.screens.cyclone_report

import com.th3pl4gu3.mauritius_emergency_services.models.api.CycloneGuideline

sealed interface CycloneGuidelinesUiState {
    data class Success(val guideline: CycloneGuideline) : CycloneGuidelinesUiState
    data object Error : CycloneGuidelinesUiState
    data object Loading : CycloneGuidelinesUiState
    data object NoNetwork: CycloneGuidelinesUiState
}