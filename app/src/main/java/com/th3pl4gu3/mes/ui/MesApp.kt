package com.th3pl4gu3.mes.ui

import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.th3pl4gu3.mes.MesApplication
import com.th3pl4gu3.mes.datastore
import com.th3pl4gu3.mes.models.AppTheme
import com.th3pl4gu3.mes.models.MesAppSettings
import com.th3pl4gu3.mes.ui.components.MesDrawer
import com.th3pl4gu3.mes.ui.components.MesTopAppBar
import com.th3pl4gu3.mes.ui.extensions.updateMesTheme
import com.th3pl4gu3.mes.ui.navigation.MesDestinations
import com.th3pl4gu3.mes.ui.navigation.MesNavGraph
import com.th3pl4gu3.mes.ui.navigation.MesNavigationActions
import com.th3pl4gu3.mes.ui.screens.theme_selector.ScreenThemeSelector
import com.th3pl4gu3.mes.ui.theme.MesTheme
import com.th3pl4gu3.mes.ui.utils.launchContactUsIntent
import kotlinx.coroutines.launch

@Composable
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
fun MesApp(
    application: MesApplication,
    startDestination: String,
    widthSizeClass: WindowWidthSizeClass
) {
    /**
     * This is the Main MES app that will
     * determine which screen content to show
     **/

    /** Load the app settings from datastore **/
    val appSettings = application.datastore.data.collectAsState(initial = MesAppSettings()).value

    /** Set the correct app theme that the user has set **/
    val darkTheme = when (appSettings.appTheme) {
        AppTheme.FOLLOW_SYSTEM -> isSystemInDarkTheme()
        AppTheme.DARK -> true
        AppTheme.LIGHT -> false
    }

    MesTheme(
        darkTheme = darkTheme // Load the app theme
    ) {


        /**
         * Define a variable to know if the screen has been expanded
         **/
        val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded

        /**
         * Define remember state variables
         **/
        val systemUiController = rememberSystemUiController()
        val navController = rememberNavController()
        val navigationActions = remember(navController) { MesNavigationActions(navController) }
        val coroutineScope = rememberCoroutineScope()
        val topAppBarState = rememberTopAppBarState()
        val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)
        var searchBarValue by remember { mutableStateOf("") }
        var showDialog by remember { mutableStateOf(value = false) }

        /**
         * Define other variables for future use
         **/
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route ?: MesDestinations.SCREEN_HOME
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)
        val activity = LocalContext.current as Activity

        /**
         * Restore System Bars color which can change
         * later in the app when launching Pre Call Screen
         **/
        if(activity.window.statusBarColor != activity.window.navigationBarColor)
            systemUiController.setSystemBarsColor(MaterialTheme.colorScheme.background)

        /**
         * Clear the search bar if we are no more in the services screen
         **/
        if(currentRoute != MesDestinations.SCREEN_SERVICES)
            searchBarValue = ""

        /**
         * Composable
         **/
        ModalNavigationDrawer(
            drawerContent = {
                MesDrawer(
                    currentRoute = currentRoute,
                    navigateToHome = navigationActions.navigateToHome,
                    navigateToServices = navigationActions.navigateToServices,
                    navigateToAbout = navigationActions.navigateToAbout,
                    navigateToSettings = navigationActions.navigateToSettings,
                    toggleThemeDialog = { coroutineScope.launch { showDialog = !showDialog } },
                    navigateToContactUs = { activity.launchContactUsIntent() },
                    closeDrawer = { coroutineScope.launch { sizeAwareDrawerState.close() } }
                )
            },
            drawerState = sizeAwareDrawerState,
            // Only enable opening the drawer via gestures if the screen is not expanded
            gesturesEnabled = !isExpandedScreen
        ) {
            Scaffold(
                topBar = {
                    if (currentRoute != MesDestinations.SCREEN_PRE_CALL) {
                        MesTopAppBar(
                            openDrawer = { coroutineScope.launch { sizeAwareDrawerState.open() } },
                            showSearchIcon = currentRoute == MesDestinations.SCREEN_SERVICES,
                            searchValue = searchBarValue,
                            searchValueChange = { searchBarValue = it }
                        )
                    }
                },
            ) { innerPadding ->

                val contentModifier = Modifier
                    .padding(innerPadding)
                    .nestedScroll(scrollBehavior.nestedScrollConnection)

                Row {
                    //                if (isExpandedScreen) {
                    //                    AppNavRail(
                    //                        currentRoute = currentRoute,
                    //                        navigateToHome = navigationActions.navigateToHome,
                    //                        navigateToInterests = navigationActions.navigateToInterests,
                    //                    )
                    //                }
                    MesNavGraph(
                        application = application,
                        searchBarValue = searchBarValue,
                        isExpandedScreen = isExpandedScreen,
                        modifier = contentModifier,
                        navController = navController,
                        openDrawer = { coroutineScope.launch { sizeAwareDrawerState.open() } },
                        startDestination = startDestination
                    )
                }

            }
        }

        if (showDialog) {
            ScreenThemeSelector(
                dialogState = { coroutineScope.launch { showDialog = !showDialog } },
                updateTheme = { coroutineScope.launch { application.updateMesTheme(it) } },
                currentAppTheme = appSettings.appTheme
            )
        }
    }
}


/**
 * Determine the drawer state to pass to the modal drawer.
 */
@Composable
@ExperimentalMaterial3Api
private fun rememberSizeAwareDrawerState(isExpandedScreen: Boolean): DrawerState {
    return if (!isExpandedScreen) {
        /**
         * If we want to allow showing the drawer, we use a real, remembered drawer
         * state defined above
         **/
        rememberDrawerState(DrawerValue.Closed)
    } else {
        /**
         * If we don't want to allow the drawer to be shown, we provide a drawer state
         * that is locked closed. This is intentionally not remembered, because we
         * don't want to keep track of any changes and always keep it closed
         **/
        DrawerState(DrawerValue.Closed)
    }

}

/**
 * Application Previews
 **/
@Preview("Mes App Light")
@Preview("Mes App Dark", uiMode =  UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
fun PreviewTopAppBarMediumSize() {

    MesTheme {
        MesApp(
            application = MesApplication(),
            startDestination = MesDestinations.SCREEN_HOME,
            widthSizeClass = WindowWidthSizeClass.Medium,
        )
    }
}

@Preview("Mes App Light")
@Preview("Mes App Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
fun PreviewTopAppBarExpandedSize() {
    MesTheme {
        MesApp(
            application = MesApplication(),
            startDestination = MesDestinations.SCREEN_HOME,
            widthSizeClass = WindowWidthSizeClass.Expanded
        )
    }
}