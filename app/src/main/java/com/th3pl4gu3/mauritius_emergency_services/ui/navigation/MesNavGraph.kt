package com.th3pl4gu3.mauritius_emergency_services.ui.navigation

import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.th3pl4gu3.mauritius_emergency_services.MesApplication
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.models.PreCallDetails
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesTwoActionDialog
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.about.ScreenAbout
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.cyclone_report.CycloneReportViewModel
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.cyclone_report.ScreenCycloneReport
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.home.HomeViewModel
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.home.ScreenHome
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.precall.PreCallViewModel
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.precall.ScreenPreCall
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.services.ScreenServices
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.services.ServicesViewModel
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.settings.ScreenSettings
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.settings.SettingsViewModel
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.welcome.ScreenWelcome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val TAG = "MES_NAV_GRAPH"

@Composable
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
fun MesNavGraph(
    application: MesApplication,
    snackBarHostState: SnackbarHostState,
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    navigationActionWrapper: NavigationActionWrapper,
    startDestination: String,
    listState: LazyListState,
    scrollState: ScrollState,
    coroutineScope: CoroutineScope,
    launchIntent: (Intent) -> Unit
) {
    /** Log information **/
    Log.i(TAG, "Starting Navigation Host")

    val telephonyManager =
        LocalContext.current.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    var serviceCallContent by remember { mutableStateOf(value = PreCallDetails()) }
    var isDialogVisible by remember { mutableStateOf(false) }

    if (isDialogVisible) {
        MesTwoActionDialog(
            title = stringResource(R.string.title_dialog_emergency_call_verifier),
            confirmButtonAction = {
                isDialogVisible = false
                coroutineScope.launch {
                    navigationActionWrapper.navigateToPreCall(
                        serviceCallContent,
                        snackBarHostState
                    )
                }
            },
            denyButtonAction = {
                isDialogVisible = false
            },
            confirmButtonLabel = stringResource(R.string.action_proceed),
            denyButtonLabel = stringResource(R.string.action_cancel),
            onDismissRequest = {
                isDialogVisible = false
            },
            content = {}
        )
    }

    // Define a navigate to pre call dependency function
    val navigateToPreCall: (service: Service, chosenNumber: String) -> Unit =
        { service, chosenNumber ->
            coroutineScope.launch {

                // Create a pre call details
                val preCallDetails = PreCallDetails(
                    serviceIdentifier = service.identifier,
                    chosenNumber = chosenNumber,
                    isEmergency = telephonyManager.isEmergencyNumber(chosenNumber)
                )

                if (preCallDetails.isEmergency) {
                    serviceCallContent = preCallDetails
                    isDialogVisible = true
                } else {
                    navigationActionWrapper.navigateToPreCall(
                        preCallDetails,
                        snackBarHostState
                    )
                }

            }
        }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(MesDestinations.SCREEN_WELCOME) {
            /** Log information **/
            Log.i(TAG, "Starting composable ${MesDestinations.SCREEN_WELCOME}")

            ScreenWelcome(
                unsetFirstTimeLogging = { application.container.dataStoreServiceRepository.unsetFirstTimeLogging() }
            )
        }
        composable(MesDestinations.SCREEN_HOME) {
            /** Log information **/
            Log.i(TAG, "Starting composable ${MesDestinations.SCREEN_HOME}")

            /** Create the view model **/
            val homeViewModel = hiltViewModel<HomeViewModel>()

            /** Launch the screen UI **/
            ScreenHome(
                homeViewModel = homeViewModel,
                navigateToPreCall = navigateToPreCall,
                scrollState = scrollState
            )
        }
        composable(MesDestinations.SCREEN_SERVICES) {
            /** Log information **/
            Log.i(TAG, "Starting composable ${MesDestinations.SCREEN_SERVICES}")

            /** Create the view model **/
            val servicesViewModel = hiltViewModel<ServicesViewModel>()

            /** Launch the screen UI **/
            ScreenServices(
                servicesViewModel = servicesViewModel,
                retryAction = servicesViewModel::reload,
                listState = listState,
                navigateToPreCall = navigateToPreCall
            )
        }
        composable(MesDestinations.SCREEN_CYCLONE_REPORT) {
            /** Log information **/
            Log.i(TAG, "Starting composable ${MesDestinations.SCREEN_CYCLONE_REPORT}")

            /** Create the view model **/
            val cycloneReportViewModel = hiltViewModel<CycloneReportViewModel>()

            /** Launch the screen UI **/
            ScreenCycloneReport(
                cycloneReportViewModel = cycloneReportViewModel
            )
        }
        composable(MesDestinations.SCREEN_PRE_CALL) {
            /** Log information **/
            Log.i(TAG, "Starting composable ${MesDestinations.SCREEN_PRE_CALL}")

            /** Create the view model **/
            val preCallViewModel = hiltViewModel<PreCallViewModel>()

            /** Launch the screen UI **/
            ScreenPreCall(
                preCallViewModel = preCallViewModel,
                closeScreen = navController::navigateUp,
                isExpandedScreen = isExpandedScreen,
                launchIntent = launchIntent
            )
        }
        composable(MesDestinations.SCREEN_ABOUT) {
            /** Log information **/
            Log.i(TAG, "Starting composable ${MesDestinations.SCREEN_ABOUT}")

            /** Launch the screen UI **/
            ScreenAbout(
                scrollState = scrollState,
                launchIntent = launchIntent
            )
        }
        composable(MesDestinations.SCREEN_SETTINGS) {
            /** Log information **/
            Log.i(TAG, "Starting composable ${MesDestinations.SCREEN_SETTINGS}")

            /** Create the view model **/
            val settingsViewModel = hiltViewModel<SettingsViewModel>()

            /** Launch the screen UI **/
            ScreenSettings(
                settingsViewModel = settingsViewModel,
                snackBarHostState = snackBarHostState,
                scrollState = scrollState,
                scope = coroutineScope
            )
        }
    }
}
