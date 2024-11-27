package com.th3pl4gu3.mauritius_emergency_services.ui.screens.cyclone_report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.GetAppLocale
import com.th3pl4gu3.mauritius_emergency_services.ui.wrappers.NetworkRequests
import com.th3pl4gu3.mauritius_emergency_services.utils.TIMEOUT_MILLIS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CycloneReportViewModel @Inject constructor(
    private val onlineServiceRequests: NetworkRequests
) : ViewModel() {

    /**
     * Create a private CycloneReportUiState to hold the MutableStateFlow of the report
     * It starts as Loading
     **/
    private val mCycloneReportUiState: MutableStateFlow<CycloneReportUiState> =
        MutableStateFlow(CycloneReportUiState.Loading)

    private val mAnimationSpeed = MutableStateFlow(0)

    /**
     * Create an accessible CycloneReportUiState for the Composable to collect
     **/
    val cycloneReportUiState: StateFlow<CycloneReportUiState> = mCycloneReportUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = CycloneReportUiState.Loading
    )

    val animationSpeed: StateFlow<Int>
        get() = mAnimationSpeed

    /**
     * On Init, load the report
     **/
    init {
        viewModelScope.launch {
            loadCycloneReport()
        }
    }

    /**
     * Private Functions
     **/
    suspend fun loadCycloneReport() {
        val report = onlineServiceRequests.getCycloneReportTesting(language = GetAppLocale)

        mCycloneReportUiState.value = if (report.success) {

            if (report.report.level > 0) {
                mAnimationSpeed.value = 4000 / report.report.level
                CycloneReportUiState.Warning(report.report)
            } else {
                mAnimationSpeed.value = 1000
                CycloneReportUiState.NoWarning
            }
        } else {
            mAnimationSpeed.value = 1000
            CycloneReportUiState.Error
        }
    }

}