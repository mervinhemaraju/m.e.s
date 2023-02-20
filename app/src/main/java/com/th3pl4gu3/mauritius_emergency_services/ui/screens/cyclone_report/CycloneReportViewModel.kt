package com.th3pl4gu3.mauritius_emergency_services.ui.screens.cyclone_report

import androidx.lifecycle.ViewModel
import com.th3pl4gu3.mauritius_emergency_services.ui.wrappers.NetworkRequests
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CycloneReportViewModel @Inject constructor(
    private val onlineServiceRequests: NetworkRequests
) : ViewModel() {

}