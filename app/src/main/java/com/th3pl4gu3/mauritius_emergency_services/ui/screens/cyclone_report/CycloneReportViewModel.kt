package com.th3pl4gu3.mauritius_emergency_services.ui.screens.cyclone_report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.th3pl4gu3.mauritius_emergency_services.models.api.CycloneGuideline
import com.th3pl4gu3.mauritius_emergency_services.models.api.CycloneName
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

    private val mCycloneReportUiState: MutableStateFlow<CycloneReportUiState> =
        MutableStateFlow(CycloneReportUiState.Loading)
    private val mCycloneNames= MutableStateFlow<List<CycloneName>>(listOf())
    private val mCycloneGuideline = MutableStateFlow(CycloneGuideline("", "", listOf()))
    private val mAnimationSpeed = MutableStateFlow(0)
    private val mCurrentCycloneLevel = MutableStateFlow(0)

    val cycloneReportUiState: StateFlow<CycloneReportUiState> = mCycloneReportUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = CycloneReportUiState.Loading
    )

    val animationSpeed: StateFlow<Int>
        get() = mAnimationSpeed

    val cycloneNames: StateFlow<List<CycloneName>>
        get() = mCycloneNames

    val cycloneGuideline: StateFlow<CycloneGuideline>
        get() = mCycloneGuideline

    /**
     * On Init, load the report
     **/
    init {
        viewModelScope.launch {
            // TODO(Load these on demand rather than all at once)
            loadCycloneReport()
            loadCycloneNames()
            loadCycloneGuidelines()
        }
    }

    /**
     * Private Functions
     **/
    suspend fun loadCycloneNames(){
        val response = onlineServiceRequests.getCycloneNames(language = GetAppLocale)

        if(response.success){
            mCycloneNames.value = response.names
        } else {
            // TODO(Trigger a snackbar for an error message)
        }

    }

    suspend fun loadCycloneGuidelines(){
        val response = onlineServiceRequests.getCycloneGuidelines(language = GetAppLocale)

        if(response.success){
            response.guidelines.forEach { cycloneGuideline ->
                if(cycloneGuideline.level == mCurrentCycloneLevel.value.toString()){
                    mCycloneGuideline.value = cycloneGuideline
                    return
                }
            }
        }

        // TODO(Add some kind of catch all for when guidelines are not found

    }

    suspend fun loadCycloneReport() {
        val report = onlineServiceRequests.getCycloneReportTesting(language = GetAppLocale)

        mCycloneReportUiState.value = if (report.success) {

            mCurrentCycloneLevel.value = report.report.level

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