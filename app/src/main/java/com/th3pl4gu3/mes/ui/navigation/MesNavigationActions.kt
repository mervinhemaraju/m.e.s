package com.th3pl4gu3.mes.ui.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.th3pl4gu3.mes.models.Service
import com.th3pl4gu3.mes.ui.navigation.MesDestinationArguments.ARG_PRE_CALL_SERVICE_IDENTIFIER

/**
 * Models the navigation actions in the app.
 */
class MesNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(MesDestinations.SCREEN_HOME) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
    val navigateToServices: () -> Unit = {
        navController.navigate(MesDestinations.SCREEN_SERVICES) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToAbout: () -> Unit = {
        navController.navigate(MesDestinations.SCREEN_ABOUT) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToSettings: () -> Unit = {
        navController.navigate(MesDestinations.SCREEN_SETTINGS) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToPreCall: (service: Service) -> Unit = {
        navController.navigate(MesDestinations.SCREEN_PRE_CALL.replace(ARG_PRE_CALL_SERVICE_IDENTIFIER, it.identifier)) {
            popUpTo(if(navController.previousBackStackEntry == null) MesDestinations.SCREEN_SERVICES else navController.previousBackStackEntry!!.id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
