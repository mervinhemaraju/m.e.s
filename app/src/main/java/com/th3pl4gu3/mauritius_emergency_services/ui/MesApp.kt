package com.th3pl4gu3.mauritius_emergency_services.ui

import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.th3pl4gu3.mauritius_emergency_services.MesApplication
import com.th3pl4gu3.mauritius_emergency_services.models.AppTheme
import com.th3pl4gu3.mauritius_emergency_services.models.MesAppSettings
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesAnimatedVisibilitySlideVerticallyContent
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesDrawer
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesNavRail
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesTopAppBar
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.launchContactUsIntent
import com.th3pl4gu3.mauritius_emergency_services.ui.navigation.MesDestinations
import com.th3pl4gu3.mauritius_emergency_services.ui.navigation.MesNavGraph
import com.th3pl4gu3.mauritius_emergency_services.ui.navigation.MesNavigationActions
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.theme_selector.ScreenThemeSelector
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme
import kotlinx.coroutines.launch

const val TAG = "MES_APP_COMPOSE"

@Composable
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
fun MesApp(
    application: MesApplication,
    appSettings: MesAppSettings,
    widthSizeClass: WindowWidthSizeClass
) {
    /**
     * This is the Main MES app that will
     * determine which screen content to show
     **/


    /** Set the correct app theme that the user has set **/
    val darkTheme = when (appSettings.appTheme) {
        AppTheme.FOLLOW_SYSTEM -> isSystemInDarkTheme()
        AppTheme.DARK -> true
        AppTheme.LIGHT -> false
    }

    MesTheme(
        darkTheme = darkTheme // Load the app theme
    ) {

        Log.i(TAG, "Settings -> ${appSettings.emergencyButtonActionIdentifier}")

        /**
         * Define the start destination
         **/
        val startDestination = if (appSettings.isFirstTimeLogging) {
            MesDestinations.SCREEN_WELCOME
        } else {
            MesDestinations.SCREEN_HOME
        }

        /**
         * Define a variable to know if the screen has been expanded
         **/
        val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded

        /**
         * Define remember state variables
         **/
        val snackBarHostState = remember { SnackbarHostState() }
        val listState = rememberLazyListState()
        val scrollState = rememberScrollState()
        val navController = rememberNavController()
        val navigationActions = remember(navController) { MesNavigationActions(navController) }
        val coroutineScope = rememberCoroutineScope()
        val topAppBarState = rememberTopAppBarState()
        val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)
        var searchBarValue by remember { mutableStateOf("") }
        var showDialog by remember { mutableStateOf(value = false) }

        val hasScrolled by remember { derivedStateOf { listState.firstVisibleItemScrollOffset > 0 }}

        /**
         * Define other variables for future use
         **/
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route ?: MesDestinations.SCREEN_HOME
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)
        val activity = LocalContext.current as Activity

        val gesturesEnabled: Boolean = !listOf(
            currentRoute == MesDestinations.SCREEN_WELCOME,
            currentRoute == MesDestinations.SCREEN_PRE_CALL,
            widthSizeClass == WindowWidthSizeClass.Expanded
        ).any { it }

        val topAppBarVisible: Boolean = !listOf(
            currentRoute == MesDestinations.SCREEN_PRE_CALL,
            currentRoute == MesDestinations.SCREEN_WELCOME,
            widthSizeClass == WindowWidthSizeClass.Expanded
        ).any { it }


        /**
         * Clear the search bar if we are no more in the services screen
         **/
        if (currentRoute != MesDestinations.SCREEN_SERVICES)
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
                    navigateToCycloneReport = navigationActions.navigateToCycloneReport,
                    navigateToAbout = navigationActions.navigateToAbout,
                    navigateToSettings = navigationActions.navigateToSettings,
                    toggleThemeDialog = { coroutineScope.launch { showDialog = !showDialog } },
                    navigateToContactUs = { activity.launchContactUsIntent() },
                    closeDrawer = { coroutineScope.launch { sizeAwareDrawerState.close() } }
                )
            },
            drawerState = sizeAwareDrawerState,
            // Only enable opening the drawer via gestures if the screen is not expanded
            gesturesEnabled = gesturesEnabled
        ) {
            Scaffold(
                topBar = {
                    MesAnimatedVisibilitySlideVerticallyContent(visibility = topAppBarVisible) {
                        MesTopAppBar(
                            openDrawer = { coroutineScope.launch { sizeAwareDrawerState.open() } },
                            showSearchIcon = currentRoute == MesDestinations.SCREEN_SERVICES,
                            searchValue = searchBarValue,
                            searchValueChange = { searchBarValue = it },
                            hasScrolled = hasScrolled
                        )
                    }
                },
                snackbarHost = {
                    SnackbarHost(snackBarHostState) { data ->
                        Snackbar(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .padding(12.dp),
                            action = {
                                TextButton(
                                    onClick = { data.dismiss() }
                                ) { Text(data.visuals.actionLabel ?: "") }
                            }
                        ) {
                            Text(data.visuals.message)
                        }
                    }
                }
            ) { innerPadding ->
                val contentModifier = Modifier
                    .padding(innerPadding)
                    .nestedScroll(scrollBehavior.nestedScrollConnection)

                Row {
                    if (isExpandedScreen) {
                        MesNavRail(
                            currentRoute = currentRoute,
                            navigateToHome = navigationActions.navigateToHome,
                            navigateToServices = navigationActions.navigateToServices,
                            navigateToCycloneReport = navigationActions.navigateToCycloneReport,
                            navigateToAbout = navigationActions.navigateToAbout,
                            navigateToSettings = navigationActions.navigateToSettings,
                            toggleThemeDialog = {
                                coroutineScope.launch {
                                    showDialog = !showDialog
                                }
                            },
                            navigateToContactUs = { activity.launchContactUsIntent() },
                            scrollState = scrollState
                        )
                    }
                    MesNavGraph(
                        application = application,
                        searchBarValue = searchBarValue,
                        snackBarHostState = snackBarHostState,
                        isExpandedScreen = isExpandedScreen,
                        modifier = contentModifier,
                        navController = navController,
                        navigationActions = navigationActions,
                        listState = listState,
                        scrollState = scrollState,
                        startDestination = startDestination,
                        coroutineScope = coroutineScope
                    )
                }

            }
        }

        if (showDialog) {
            ScreenThemeSelector(
                dialogState = { coroutineScope.launch { showDialog = !showDialog } },
                updateTheme = {
                    coroutineScope.launch {
                        application.container.dataStoreServiceRepository.updateTheme(
                            it
                        )
                    }
                },
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
@Preview("Mes App Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
fun PreviewTopAppBarMediumSize() {
    MesTheme {
        MesApp(
            application = MesApplication(),
            widthSizeClass = WindowWidthSizeClass.Medium,
            appSettings = MesAppSettings()
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
@ExperimentalAnimationApi
fun PreviewTopAppBarExpandedSize() {
    MesTheme {
        MesApp(
            application = MesApplication(),
            widthSizeClass = WindowWidthSizeClass.Expanded,
            appSettings = MesAppSettings()
        )
    }
}