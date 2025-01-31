package com.th3pl4gu3.mauritius_emergency_services.ui.navigation

import androidx.compose.material3.SnackbarHostState
import com.th3pl4gu3.mauritius_emergency_services.MesApplication
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.models.PreCallDetails
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.HasNecessaryPermissions

class NavigationActionWrapper(
    private val application: MesApplication,
    private val actions: MesNavigationActions
){

    suspend fun navigateToPreCall(preCallDetails: PreCallDetails, snackBarHostState: SnackbarHostState){
        if (application.applicationContext.HasNecessaryPermissions) {
            actions.navigateToPreCall(preCallDetails)
        } else {
            snackBarHostState.showSnackbar(
                application.resources.getString(R.string.message_permissions_enable_phone_call)
            )
        }
    }
}
