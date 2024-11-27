package com.th3pl4gu3.mauritius_emergency_services.ui.screens.cyclone_report

import com.th3pl4gu3.mauritius_emergency_services.models.api.CycloneReport

sealed interface CycloneReportUiState {
    data class Warning(val report: CycloneReport) : CycloneReportUiState
    data object Error : CycloneReportUiState
    data object Loading : CycloneReportUiState
    data object NoWarning: CycloneReportUiState
}