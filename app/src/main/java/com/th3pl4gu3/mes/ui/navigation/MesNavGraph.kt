package com.th3pl4gu3.mes.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.th3pl4gu3.mes.data.network.AppContainer
import com.th3pl4gu3.mes.ui.screens.ScreenAbout
import com.th3pl4gu3.mes.ui.screens.home.ScreenHome
import com.th3pl4gu3.mes.ui.screens.services.ScreenServices
import com.th3pl4gu3.mes.ui.screens.ScreenSettings
import com.th3pl4gu3.mes.ui.screens.home.HomeViewModel
import com.th3pl4gu3.mes.ui.screens.services.ServicesViewModel

@Composable
@ExperimentalMaterial3Api
fun MesNavGraph(
    appContainer: AppContainer,
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MesDestinations.SCREEN_HOME,
    openDrawer: () -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(MesDestinations.SCREEN_HOME) {
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.provideFactory(appContainer = appContainer)
            )
            ScreenHome(
                homeUiState = homeViewModel.homeUiState,
                retryAction = homeViewModel::getServices
            )
        }
        composable(MesDestinations.SCREEN_SERVICES) {
            val servicesViewModel: ServicesViewModel = viewModel(
                factory = ServicesViewModel.provideFactory(appContainer = appContainer)
            )
            ScreenServices(
                servicesUiState = servicesViewModel.servicesUiState,
                retryAction = servicesViewModel::getServices
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
