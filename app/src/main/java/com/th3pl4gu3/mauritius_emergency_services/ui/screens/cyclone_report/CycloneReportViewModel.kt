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

    /**
     * Define private UI states
     **/
    private val mCycloneReportUiState: MutableStateFlow<CycloneReportUiState> =
        MutableStateFlow(CycloneReportUiState.Loading)

    private val mCycloneNamesUiState: MutableStateFlow<CycloneNamesUiState> =
        MutableStateFlow(CycloneNamesUiState.Loading)

    private val mCycloneGuidelinesUiState: MutableStateFlow<CycloneGuidelinesUiState> =
        MutableStateFlow(CycloneGuidelinesUiState.Loading)

    private val mAnimationSpeed = MutableStateFlow(0)
    private val mCurrentCycloneLevel = MutableStateFlow(0)

    /**
     * Define public accessible UI states
     **/
    val cycloneReportUiState: StateFlow<CycloneReportUiState> = mCycloneReportUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = CycloneReportUiState.Loading
    )

    val cycloneNamesUiState: StateFlow<CycloneNamesUiState> = mCycloneNamesUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = CycloneNamesUiState.Loading
    )

    val cycloneGuidelinesUiState: StateFlow<CycloneGuidelinesUiState> =
        mCycloneGuidelinesUiState.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = CycloneGuidelinesUiState.Loading
        )

    val animationSpeed: StateFlow<Int>
        get() = mAnimationSpeed

    val currentCycloneLevel: StateFlow<Int>
        get() = mCurrentCycloneLevel

    /**
     * On Init, load the data
     **/
    init {
        viewModelScope.launch {
            // Load the data
            reload()
        }
    }

    /**
     * Public Functions
     **/
    suspend fun reload() {
        // Load the cyclone report
        loadCycloneReport()

        // Load the cyclone guidelines
        loadCycloneGuidelines()

        // Load the cyclone names
        loadCycloneNames()
    }

    /**
     * Private Functions
     **/
    private suspend fun loadCycloneNames() {
        with(getCycloneNames()) {
            mCycloneNamesUiState.value = if (this.success) {
                CycloneNamesUiState.Success(this.names)
            } else {
                CycloneNamesUiState.Error
            }
        }
    }

    private suspend fun loadCycloneGuidelines() {

        with(getCycloneGuidelines()) {
            if (this.success) {
                this.guidelines.forEach { cycloneGuideline ->
                    if (cycloneGuideline.level == mCurrentCycloneLevel.value.toString()) {
                        mCycloneGuidelinesUiState.value =
                            CycloneGuidelinesUiState.Success(cycloneGuideline)
                        return
                    }
                }

            } else {
                mCycloneNamesUiState.value = CycloneNamesUiState.Error
            }
        }

        // Default error if no guidelines were found
        mCycloneNamesUiState.value = CycloneNamesUiState.Error
    }

    private suspend fun loadCycloneReport() {

        with(getCycloneReport()) {

            mCycloneReportUiState.value = if (this.success) {

                mCurrentCycloneLevel.value = this.report.level

                if (this.report.level > 0) {
                    mAnimationSpeed.value = 4000 / this.report.level
                    CycloneReportUiState.Warning(this.report)
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


    /**
     * Private Functions
     **/
    private suspend fun getCycloneNames() =
        onlineServiceRequests.getCycloneNames(language = GetAppLocale)

    private suspend fun getCycloneGuidelines() =
        onlineServiceRequests.getCycloneGuidelines(language = GetAppLocale)

    private suspend fun getCycloneReport() =
        onlineServiceRequests.getCycloneReportTesting(language = GetAppLocale)
}