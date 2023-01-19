package com.th3pl4gu3.mes.ui.navigation

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.th3pl4gu3.mes.MesApplication
import com.th3pl4gu3.mes.data.AppContainer
import com.th3pl4gu3.mes.datastore
import com.th3pl4gu3.mes.models.MesAppSettings
import com.th3pl4gu3.mes.ui.screens.ScreenAbout
import com.th3pl4gu3.mes.ui.screens.home.ScreenHome
import com.th3pl4gu3.mes.ui.screens.services.ScreenServices
import com.th3pl4gu3.mes.ui.screens.ScreenSettings
import com.th3pl4gu3.mes.ui.screens.home.HomeUiState
import com.th3pl4gu3.mes.ui.screens.home.HomeViewModel
import com.th3pl4gu3.mes.ui.screens.precall.PreCallUiState
import com.th3pl4gu3.mes.ui.screens.precall.PreCallViewModel
import com.th3pl4gu3.mes.ui.screens.precall.ScreenPreCall
import com.th3pl4gu3.mes.ui.screens.services.ServicesViewModel

@Composable
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
fun MesNavGraph(
    appContainer: AppContainer,
    searchBarValue: String,
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MesDestinations.SCREEN_HOME,
    openDrawer: () -> Unit = {}
) {

    val navigationActions = remember(navController) { MesNavigationActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(MesDestinations.SCREEN_HOME) {

            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.provideFactory(
                    appContainer = appContainer
                )
            )

            val homeUiState: HomeUiState by homeViewModel.homeUiState.collectAsState()

            ScreenHome(
                homeUiState = homeUiState,
                retryAction = homeViewModel::loadOnlineServices,
                navigateToPreCall = navigationActions.navigateToPreCall
            )
        }
        composable(MesDestinations.SCREEN_SERVICES) {

            val servicesViewModel: ServicesViewModel = viewModel(
                factory = ServicesViewModel.provideFactory(appContainer = appContainer)
            )
            servicesViewModel.search(searchBarValue)

            val servicesUiState by servicesViewModel.servicesUiState.collectAsState()

            ScreenServices(
                servicesUiState = servicesUiState,
                retryAction = servicesViewModel::loadOnlineServices,
                navigateToPreCall = navigationActions.navigateToPreCall
            )
        }
        composable(MesDestinations.SCREEN_PRE_CALL) { backStackEntry ->
            val preCallViewModel: PreCallViewModel = viewModel(
                factory = PreCallViewModel.provideFactory(
                    serviceIdentifier = backStackEntry.arguments?.getString("serviceIdentifier"),
                    appContainer = appContainer
                )
            )

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
            ScreenSettings()
        }
    }
}
