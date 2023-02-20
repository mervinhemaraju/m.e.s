package com.th3pl4gu3.mauritius_emergency_services.ui.screens.cyclone_report

import com.th3pl4gu3.mauritius_emergency_services.models.CycloneReport

sealed interface CycloneReportUiState {
    data class Warning(val services: List<CycloneReport>) : CycloneReportUiState
    object Error : CycloneReportUiState
    object Loading : CycloneReportUiState
    object NoWarning: CycloneReportUiState
}