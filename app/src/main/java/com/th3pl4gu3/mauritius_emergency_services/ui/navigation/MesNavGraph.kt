package com.th3pl4gu3.mauritius_emergency_services.ui.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.th3pl4gu3.mauritius_emergency_services.models.MesAppSettings
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.about.ScreenAbout
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.home.HomeUiState
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.home.HomeViewModel
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.home.ScreenHome
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.precall.PreCallViewModel
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.precall.ScreenPreCall
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.services.ScreenServices
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.services.ServicesViewModel
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.settings.ScreenSettings
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.settings.SettingsViewModel
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.welcome.ScreenWelcome

const val TAG = "MES_NAV_GRAPH"

@Composable
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
fun MesNavGraph(
    application: MesApplication,
    searchBarValue: String,
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String,
    listState: LazyListState,
    scrollState: ScrollState,
    openDrawer: () -> Unit = {}
) {
    // Log info
    Log.i(TAG, "Starting Navigation Host")

    val navigationActions = remember(navController) { MesNavigationActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(MesDestinations.SCREEN_WELCOME) {
            // Log info
            Log.i(TAG, "Starting composable ${MesDestinations.SCREEN_WELCOME}")

            ScreenWelcome(
                unsetFirstTimeLogging = { application.container.dataStoreServiceRepository.unsetFirstTimeLogging() }
            )
        }
        composable(MesDestinations.SCREEN_HOME) {
            // Log info
            Log.i(TAG, "Starting composable ${MesDestinations.SCREEN_HOME}")

            val homeViewModel = hiltViewModel<HomeViewModel>()

            val homeUiState: HomeUiState by homeViewModel.homeUiState.collectAsState()

            val mesAppSettings = homeViewModel.mesAppSettings.collectAsState(initial = MesAppSettings()).value

            ScreenHome(
                homeUiState = homeUiState,
                retryAction = homeViewModel::refresh,
                navigateToPreCall = navigationActions.navigateToPreCall,
                mesAppSettings = mesAppSettings,
                scrollState = scrollState
            )
        }
        composable(MesDestinations.SCREEN_SERVICES) {
            // Log info
            Log.i(TAG, "Starting composable ${MesDestinations.SCREEN_SERVICES}")

            val servicesViewModel = hiltViewModel<ServicesViewModel>()

            servicesViewModel.search(searchBarValue)

            val servicesUiState by servicesViewModel.servicesUiState.collectAsState()

            ScreenServices(
                servicesUiState = servicesUiState,
                retryAction = servicesViewModel::loadOnlineServices,
                listState = listState,
                navigateToPreCall = navigationActions.navigateToPreCall
            )
        }
        composable(MesDestinations.SCREEN_PRE_CALL) {
            // Log info
            Log.i(TAG, "Starting composable ${MesDestinations.SCREEN_PRE_CALL}")

            val preCallViewModel = hiltViewModel<PreCallViewModel>()

            val preCallUiState by preCallViewModel.service.collectAsState()

            val countdown by preCallViewModel.seconds.collectAsState(initial = 5)

            val startCall: Boolean by preCallViewModel.startCall.collectAsState()


            ScreenPreCall(
                preCallUiState = preCallUiState,
                startCall = startCall,
                closeScreen = navController::navigateUp,
                countdown = countdown.toString()
            )
        }
        composable(MesDestinations.SCREEN_ABOUT) {
            // Log info
            Log.i(TAG, "Starting composable ${MesDestinations.SCREEN_ABOUT}")

            ScreenAbout(
                scrollState = scrollState
            )
        }
        composable(MesDestinations.SCREEN_SETTINGS) {
            // Log info
            Log.i(TAG, "Starting composable ${MesDestinations.SCREEN_SETTINGS}")

            val settingsViewModel = hiltViewModel<SettingsViewModel>()

            val message = settingsViewModel.messageQueue.collectAsState(initial = null).value

            val services = settingsViewModel.services.collectAsState(initial = listOf()).value

            if (message != null) {

                Toast.makeText(
                    LocalContext.current,
                    if(!message.first.isNullOrEmpty()){
                        stringResource(id = message.second, message.first!!)
                    }else{
                        stringResource(id = message.second)
                    },
                    Toast.LENGTH_SHORT
                ).show()

                settingsViewModel.clearMessageQueue()
            }

            ScreenSettings(
                emergencyServices = services,
                forceRefreshServices = {
                    settingsViewModel.forceRefreshServices()
                },
                updateEmergencyButtonAction = { settingsViewModel.updateEmergencyButtonAction(it) },
                scrollState = scrollState
            )
        }
    }
}
