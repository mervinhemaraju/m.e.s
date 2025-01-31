package com.th3pl4gu3.mauritius_emergency_services.ui

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.th3pl4gu3.mauritius_emergency_services.MesApplication
import com.th3pl4gu3.mauritius_emergency_services.models.MesAppSettings
import com.th3pl4gu3.mauritius_emergency_services.models.PreCallDetails
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesNavigationDrawer
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesSearchTopBar
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesSimpleTopBar
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.capitalize
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.getContactUsIntent
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.launchEmailIntent
import com.th3pl4gu3.mauritius_emergency_services.ui.navigation.MesDestinations
import com.th3pl4gu3.mauritius_emergency_services.ui.navigation.MesNavGraph
import com.th3pl4gu3.mauritius_emergency_services.ui.navigation.MesNavigationActions
import com.th3pl4gu3.mauritius_emergency_services.ui.navigation.NavigationActionWrapper
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
    widthSizeClass: WindowWidthSizeClass,
    searchOfflineServices: (String) -> Unit,
    services: List<Service>,
    launchIntent: (Intent) -> Unit
) {
    /**
     * This is the Main MES app that will
     * determine which screen content to show
     **/


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
    val navigationActionWrapper = NavigationActionWrapper(application, navigationActions)
    val coroutineScope = rememberCoroutineScope()
    val topAppBarState = rememberTopAppBarState()
    val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)
    var showDialog by remember { mutableStateOf(value = false) }

    /**
     * Define other variables for future use
     **/
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: MesDestinations.SCREEN_HOME
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    val gesturesEnabled: Boolean = !listOf(
        currentRoute == MesDestinations.SCREEN_WELCOME,
        currentRoute == MesDestinations.SCREEN_PRE_CALL,
        currentRoute == MesDestinations.SCREEN_SETTINGS,
        currentRoute == MesDestinations.SCREEN_ABOUT
    ).any { it }

    /** Top App Bar is not visible on these conditions **/
    val topAppBarVisible: Boolean = !listOf(
        currentRoute == MesDestinations.SCREEN_PRE_CALL,
        currentRoute == MesDestinations.SCREEN_WELCOME
    ).any { it }

    /** Search Bar is only visible on these conditions **/
    val searchTopBarVisible: Boolean = listOf(
        currentRoute == MesDestinations.SCREEN_HOME,
        currentRoute == MesDestinations.SCREEN_SERVICES,
        currentRoute == MesDestinations.SCREEN_CYCLONE_REPORT
    ).any { it }

    var text by rememberSaveable { mutableStateOf("") }
    var searchBarExpanded by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val onSearchExpandChange: (Boolean) -> Unit = { value ->
        searchBarExpanded = value
    }

    /**
     * Composable
     **/
    ModalNavigationDrawer(
        drawerContent = {
            MesNavigationDrawer(
                currentRoute = currentRoute,
                navigateToHome = navigationActions.navigateToHome,
                navigateToServices = navigationActions.navigateToServices,
                navigateToCycloneReport = navigationActions.navigateToCycloneReport,
                navigateToAbout = navigationActions.navigateToAbout,
                navigateToSettings = navigationActions.navigateToSettings,
                toggleThemeDialog = { coroutineScope.launch { showDialog = !showDialog } },
                navigateToContactUs = {
                    launchIntent(
                        getContactUsIntent()
                    )
                },
                closeDrawer = { coroutineScope.launch { sizeAwareDrawerState.close() } }
            )
        },
        drawerState = sizeAwareDrawerState,
        gesturesEnabled = gesturesEnabled
    ) {
        Scaffold(
            topBar = {
                if (topAppBarVisible) {
                    if (searchTopBarVisible) {
                        MesSearchTopBar(
                            query = text,
                            expanded = searchBarExpanded,
                            onExpandedChange = onSearchExpandChange,
                            onSearch = { focusManager.clearFocus() },
                            closeSearch = { searchBarExpanded = false },
                            openDrawer =
                            { coroutineScope.launch { sizeAwareDrawerState.open() } },
                            onSearchQueryChange = {
                                text = it
                                searchOfflineServices(it)
                            },
                            services = services,
                            onServiceClick = {
                                coroutineScope.launch {
                                    navigationActionWrapper.navigateToPreCall(
                                        PreCallDetails(
                                            it.identifier,
                                            it.main_contact.toString(),
                                            false
                                        ),
                                        snackBarHostState
                                    )
                                }
                            },
                            onExtrasClick = { service, contact ->
                                if (contact.isDigitsOnly()) {
                                    coroutineScope.launch {
                                        navigationActionWrapper.navigateToPreCall(
                                            PreCallDetails(
                                                service.identifier,
                                                contact,
                                                false
                                            ),
                                            snackBarHostState
                                        )
                                    }
                                } else {
                                    context.launchEmailIntent(recipient = contact)
                                }
                            },
                            clearSearch = { text = "" }
                        )
                    } else {
                        MesSimpleTopBar(
                            screenTitle = currentRoute.replace("_", " ").capitalize(),
                            backButtonAction = { navController.navigateUp() }
                        )
                    }
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
                MesNavGraph(
                    application = application,
                    snackBarHostState = snackBarHostState,
                    isExpandedScreen = isExpandedScreen,
                    modifier = contentModifier,
                    navController = navController,
                    navigationActionWrapper = navigationActionWrapper,
                    listState = listState,
                    scrollState = scrollState,
                    startDestination = startDestination,
                    coroutineScope = coroutineScope,
                    launchIntent = launchIntent
                )
            }

        }
    }

    if (showDialog) {
        ScreenThemeSelector(
            dialogState = { coroutineScope.launch { showDialog = !showDialog } },
            updateTheme = { appTheme, colorContrast ->
                coroutineScope.launch {
                    if (appTheme != appSettings.appTheme) {
                        application.container.dataStoreServiceRepository.updateTheme(
                            appTheme
                        )
                    }

                    if (colorContrast != appSettings.appColorContrast) {
                        application.container.dataStoreServiceRepository.updateColorContrast(
                            colorContrast
                        )
                    }

                }
            },
            currentAppTheme = appSettings.appTheme,
            currentColorContrast = appSettings.appColorContrast,
            dynamicColorsEnabled = appSettings.dynamicColorsEnabled
        )
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
@Preview("Mes App Welcome Medium Size Light")
@Preview("Mes App Welcome Medium Size Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
fun WelcomeTopAppBarMediumSizePreview() {
    val mockAppSettings = MesAppSettings(isFirstTimeLogging = true)
    MesTheme {
        MesApp(
            application = MesApplication(),
            widthSizeClass = WindowWidthSizeClass.Medium,
            appSettings = mockAppSettings,
            searchOfflineServices = {},
            services = listOf(),
            launchIntent = {}
        )
    }
}

@Preview("Mes App Home Medium Size Light")
@Preview("Mes App Home Medium Size Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
fun HomeTopAppBarMediumSizePreview() {
    val mockAppSettings = MesAppSettings(isFirstTimeLogging = false)
    MesTheme {
        MesApp(
            application = MesApplication(),
            widthSizeClass = WindowWidthSizeClass.Medium,
            appSettings = mockAppSettings,
            searchOfflineServices = {},
            services = listOf(),
            launchIntent = {}
        )
    }
}

@Preview("Mes App Welcome Expanded Size Light")
@Preview("Mes App Welcome Expanded Size Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
fun WelcomeTopAppBarExpandedSizePreview() {
    val mockAppSettings = MesAppSettings(isFirstTimeLogging = true)
    MesTheme {
        MesApp(
            application = MesApplication(),
            widthSizeClass = WindowWidthSizeClass.Expanded,
            appSettings = mockAppSettings,
            searchOfflineServices = {},
            services = listOf(),
            launchIntent = {}
        )
    }
}

@Preview("Mes App Home Expanded Size Light")
@Preview("Mes App Home Expanded Size Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
fun HomeTopAppBarExpandedSizePreview() {
    val mockAppSettings = MesAppSettings(isFirstTimeLogging = false)
    MesTheme {
        MesApp(
            application = MesApplication(),
            widthSizeClass = WindowWidthSizeClass.Expanded,
            appSettings = mockAppSettings,
            searchOfflineServices = {},
            services = listOf(),
            launchIntent = {}
        )
    }
}