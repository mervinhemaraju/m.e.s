package com.th3pl4gu3.mes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.th3pl4gu3.mes.ui.screens.ScreenAbout
import com.th3pl4gu3.mes.ui.screens.ScreenHome
import com.th3pl4gu3.mes.ui.screens.ScreenServices
import com.th3pl4gu3.mes.ui.screens.ScreenSettings

@Composable
fun MesNavGraph(
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
//            val homeViewModel: HomeViewModel = viewModel(
//                factory = HomeViewModel.provideFactory(appContainer.postsRepository)
//            )
            ScreenHome()
        }
        composable(MesDestinations.SCREEN_SERVICES) {
//            val homeViewModel: HomeViewModel = viewModel(
//                factory = HomeViewModel.provideFactory(appContainer.postsRepository)
//            )
            ScreenServices()
        }
        composable(MesDestinations.SCREEN_ABOUT) {
//            val interestsViewModel: InterestsViewModel = viewModel(
//                factory = InterestsViewModel.provideFactory(appContainer.interestsRepository)
//            )
            ScreenAbout()
        }
        composable(MesDestinations.SCREEN_SETTINGS) {
//            val homeViewModel: HomeViewModel = viewModel(
//                factory = HomeViewModel.provideFactory(appContainer.postsRepository)
//            )
            ScreenSettings()
        }
    }
}
