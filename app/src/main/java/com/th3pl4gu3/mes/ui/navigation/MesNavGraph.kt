package com.th3pl4gu3.mes.ui.navigation

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.th3pl4gu3.mes.MesApplication
import com.th3pl4gu3.mes.models.MesAppSettings
import com.th3pl4gu3.mes.ui.screens.about.ScreenAbout
import com.th3pl4gu3.mes.ui.screens.home.HomeUiState
import com.th3pl4gu3.mes.ui.screens.home.HomeViewModel
import com.th3pl4gu3.mes.ui.screens.home.ScreenHome
import com.th3pl4gu3.mes.ui.screens.precall.PreCallViewModel
import com.th3pl4gu3.mes.ui.screens.precall.ScreenPreCall
import com.th3pl4gu3.mes.ui.screens.services.ScreenServices
import com.th3pl4gu3.mes.ui.screens.services.ServicesViewModel
import com.th3pl4gu3.mes.ui.screens.settings.ScreenSettings
import com.th3pl4gu3.mes.ui.screens.settings.SettingsViewModel
import com.th3pl4gu3.mes.ui.screens.welcome.ScreenWelcome

@Composable
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
fun MesNavGraph(
    application: MesApplication,
    searchBarValue: String,
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String,
    openDrawer: () -> Unit = {}
) {

    val navigationActions = remember(navController) { MesNavigationActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(MesDestinations.SCREEN_WELCOME) {
            ScreenWelcome(
                unsetFirstTimeLogging = { application.container.dataStoreServiceRepository.unsetFirstTimeLogging() }
            )
        }
        composable(MesDestinations.SCREEN_HOME) {

            val homeViewModel = hiltViewModel<HomeViewModel>()

            val homeUiState: HomeUiState by homeViewModel.homeUiState.collectAsState()

            val mesAppSettings = homeViewModel.mesAppSettings.collectAsState(initial = MesAppSettings()).value

            ScreenHome(
                homeUiState = homeUiState,
                retryAction = homeViewModel::loadOnlineServices,
                navigateToPreCall = navigationActions.navigateToPreCall,
                mesAppSettings = mesAppSettings
            )
        }
        composable(MesDestinations.SCREEN_SERVICES) {

            val servicesViewModel = hiltViewModel<ServicesViewModel>()

            servicesViewModel.search(searchBarValue)

            val servicesUiState by servicesViewModel.servicesUiState.collectAsState()

            ScreenServices(
                servicesUiState = servicesUiState,
                retryAction = servicesViewModel::loadOnlineServices,
                navigateToPreCall = navigationActions.navigateToPreCall
            )
        }
        composable(
            MesDestinations.SCREEN_PRE_CALL,
        ) {

            val preCallViewModel = hiltViewModel<PreCallViewModel>()

            val preCallUiState by preCallViewModel.service.collectAsState()

            val countdown by preCallViewModel.tick.observeAsState()

            val startCall by preCallViewModel.startCall.observeAsState()

            ScreenPreCall(
                preCallUiState = preCallUiState,
                startCall = startCall,
                closeScreen = navController::popBackStack,
                countdown = countdown
            )
        }
        composable(MesDestinations.SCREEN_ABOUT) {
            ScreenAbout()
        }
        composable(MesDestinations.SCREEN_SETTINGS) {

            val settingsViewModel = hiltViewModel<SettingsViewModel>()

            val message= settingsViewModel.messageQueue.collectAsState(initial = null).value

            val services = settingsViewModel.services.collectAsState(initial = listOf()).value

            if (!message.isNullOrEmpty()) {

                Toast.makeText(
                    LocalContext.current,
                    message,
                    Toast.LENGTH_SHORT
                ).show()

                settingsViewModel.clearMessageQueue()
            }

            ScreenSettings(
                emergencyServices = services,
                forceRefreshServices = {
                    settingsViewModel.forceRefreshServices()
                },
                updateEmergencyButtonAction = { settingsViewModel.updateEmergencyButtonAction(it) }
            )
        }
    }
}
