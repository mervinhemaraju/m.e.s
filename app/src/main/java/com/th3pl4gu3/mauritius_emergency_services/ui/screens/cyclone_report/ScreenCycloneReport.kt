package com.th3pl4gu3.mauritius_emergency_services.ui.screens.cyclone_report

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.models.api.CycloneReport
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesCounter
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesIcon
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesScreenAnimatedLoading
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesScreenError
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.capitalize
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.elevation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ScreenCycloneReport(
    cycloneReportViewModel: CycloneReportViewModel
) {

    /** Get the UI State from the view model **/
    val cycloneReportUiState by cycloneReportViewModel.cycloneReportUiState.collectAsState()
    val cycloneNamesUiState by cycloneReportViewModel.cycloneNamesUiState.collectAsState()
    val cycloneGuidelinesUiState by cycloneReportViewModel.cycloneGuidelinesUiState.collectAsState()

    var showCycloneNamesBottomSheet by remember { mutableStateOf(false) }
    var showCycloneGuidelinesBottomSheet by remember { mutableStateOf(false) }

    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val ptrState = rememberPullToRefreshState()
    val animationSpeed: Int by cycloneReportViewModel.animationSpeed.collectAsState()
    val currentCycloneLevel: Int by cycloneReportViewModel.currentCycloneLevel.collectAsState()

    val retryAction: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            cycloneReportViewModel.reload()
            isRefreshing = false
        }
    }

    CycloneReportDecisionsUi(
        cycloneReportUiState = cycloneReportUiState,
        showCycloneGuidelinesOnClick = { showCycloneGuidelinesBottomSheet = true },
        showCycloneNamesOnClick = { showCycloneNamesBottomSheet = true },
        isRefreshing = isRefreshing,
        ptrState = ptrState,
        animationSpeed = animationSpeed,
        retryAction = retryAction
    )

    SheetCycloneNamesDecisionsUi(
        showSheetCycloneNames = showCycloneNamesBottomSheet,
        cycloneNamesUiState = cycloneNamesUiState,
        onDismissSheetCycloneNames = { showCycloneNamesBottomSheet = false },
        retryAction = retryAction
    )

    SheetCycloneGuidelinesDecisionsUi(
        currentCycloneLevel = currentCycloneLevel,
        showSheetCycloneGuidelines = showCycloneGuidelinesBottomSheet,
        cycloneGuidelinesUiState = cycloneGuidelinesUiState,
        onDismissSheetCycloneNames = { showCycloneGuidelinesBottomSheet = false },
        retryAction = retryAction
    )
}

/**
 * The decision make for Ui State
 **/
@Composable
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun CycloneReportDecisionsUi(
    cycloneReportUiState: CycloneReportUiState,
    isRefreshing: Boolean,
    retryAction: () -> Unit,
    showCycloneNamesOnClick: () -> Unit,
    showCycloneGuidelinesOnClick: () -> Unit,
    ptrState: PullToRefreshState,
    animationSpeed: Int
) {
    when (cycloneReportUiState) {
        is CycloneReportUiState.Loading -> MesScreenAnimatedLoading(
            loadingMessage = stringResource(R.string.message_loading_basic),
            modifier = Modifier.fillMaxSize()
        )

        is CycloneReportUiState.Warning -> ScreenWarning(
            report = cycloneReportUiState.report,
            onRefresh = retryAction,
            isRefreshing = isRefreshing,
            ptrState = ptrState,
            animationSpeed = animationSpeed,
            showCycloneNamesOnClick = showCycloneNamesOnClick,
            showCycloneGuidelinesOnClick = showCycloneGuidelinesOnClick,
        )

        is CycloneReportUiState.NoWarning -> ScreenNoWarning(
            onRefresh = retryAction,
            isRefreshing = isRefreshing,
            ptrState = ptrState,
            showCycloneNamesOnClick = showCycloneNamesOnClick
        )

        is CycloneReportUiState.Error -> MesScreenError(
            retryAction = retryAction,
            errorMessageId = R.string.message_error_loading_cyclone_report_failed,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )

        is CycloneReportUiState.NoNetwork -> MesScreenError(
            retryAction = retryAction,
            errorMessageId = R.string.message_internet_connection_needed,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )
    }
}

/** Screens **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenWarning(
    report: CycloneReport,
    scrollState: ScrollState = rememberScrollState(),
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    ptrState: PullToRefreshState,
    animationSpeed: Int,
    showCycloneNamesOnClick: () -> Unit,
    showCycloneGuidelinesOnClick: () -> Unit,
) {

    var angle by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(animationSpeed) {
        while (true) {
            val frameTimeMillis = 16 // Approximate frame time for 60 FPS
            val increment = (360f / animationSpeed) * frameTimeMillis
            angle = (angle + increment) % 360
            delay(frameTimeMillis.toLong())
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp, top = 16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        PullToRefreshBox(
            modifier = Modifier.padding(8.dp),
            state = ptrState,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .verticalScroll(scrollState)
            ) {

                Text(
                    text = stringResource(R.string.title_cyclone_report_primary),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 4.dp)
                )

                Text(
                    text = stringResource(
                        R.string.title_cyclone_report_status,
                        report.level.toString()
                    ),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 4.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.size(34.dp))

                MesIcon(
                    painterResource = R.drawable.ic_cyclone,
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = report.level.toString(),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .graphicsLayer {
                            rotationZ = angle
                        }
                )

                Spacer(modifier = Modifier.size(48.dp))

                WidgetNextBulletin(
                    hour = report.getTimeSlices().first,
                    minute = report.getTimeSlices().second,
                    second = report.getTimeSlices().third
                )

                Spacer(modifier = Modifier.size(56.dp))

                WidgetLatestNews(news = report.news)
            }

            WidgetActionButtons(
                namesButtonOnClick = showCycloneNamesOnClick,
                guidelinesButtonOnClick = showCycloneGuidelinesOnClick,
                modifier = Modifier.align(Alignment.BottomEnd),
                scrollState = scrollState
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenNoWarning(
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    ptrState: PullToRefreshState,
    showCycloneNamesOnClick: () -> Unit,
    scrollState: ScrollState = rememberScrollState(),
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp, top = 16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        PullToRefreshBox(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            state = ptrState,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    MesIcon(
                        painterResource = R.drawable.ic_cloud,
                        tint = MaterialTheme.colorScheme.secondary,
                        contentDescription = R.string.message_cyclone_report_no_warnings,
                        modifier = Modifier
                    )
                    MesIcon(
                        painterResource = R.drawable.ic_cloud,
                        tint = MaterialTheme.colorScheme.secondary,
                        contentDescription = R.string.message_cyclone_report_no_warnings,
                        modifier = Modifier
                    )
                }

                Text(
                    text = stringResource(R.string.message_cyclone_report_no_warnings),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                )

                MesIcon(
                    painterResource = R.drawable.ic_cloud,
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = R.string.message_cyclone_report_no_warnings,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            WidgetActionButtons(
                namesButtonOnClick = showCycloneNamesOnClick,
                guidelinesButtonOnClick = {},
                modifier = Modifier.align(Alignment.BottomEnd),
                scrollState = scrollState,
                showGuidelinesButton = false
            )
        }
    }
}

/**
 * Widgets
 **/
@Composable
fun WidgetLatestNews(news: List<String>) {
    Text(
        text = stringResource(R.string.title_cyclone_report_latest_news).capitalize(),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
    )

    Spacer(modifier = Modifier.size(8.dp))

    LazyRow(
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp),
        modifier = Modifier.padding(bottom = 80.dp)
    ) {
        items(news) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.outlinedCardElevation(
                    elevation.superLow
                ),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .width(280.dp)
                    .height(240.dp)
            ) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun WidgetNextBulletin(hour: String = "00", minute: String = "00", second: String = "00") {

    val backgroundThemeModifier = Modifier
        .clip(MaterialTheme.shapes.large)
        .background(MaterialTheme.colorScheme.surfaceContainerLow)

    Text(
        text = stringResource(R.string.title_cyclone_report_next_bulletin).capitalize(),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    )

    Spacer(modifier = Modifier.size(8.dp))

    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {

        MesCounter(
            countdown = hour,
            label = stringResource(R.string.label_time_hour),
            modifier = backgroundThemeModifier
        )

        Text(
            text = stringResource(R.string.label_time_separator),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(4.dp, 0.dp)
                .align(Alignment.CenterVertically)
        )

        MesCounter(
            countdown = minute,
            label = stringResource(R.string.label_time_minute),
            modifier = backgroundThemeModifier
        )

        Text(
            text = stringResource(R.string.label_time_separator),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(4.dp, 0.dp)
                .align(Alignment.CenterVertically)
        )

        MesCounter(
            countdown = second,
            label = stringResource(R.string.label_time_second),
            modifier = backgroundThemeModifier
        )
    }
}

@Composable
fun WidgetActionButtons(
    namesButtonOnClick: () -> Unit,
    guidelinesButtonOnClick: () -> Unit,
    modifier: Modifier,
    scrollState: ScrollState,
    showGuidelinesButton: Boolean = true,
) {
    val expandedFab by remember { derivedStateOf { scrollState.value <= 50 } }

    Column(
        modifier = modifier,
    ) {

        if(showGuidelinesButton){
            FloatingActionButton(
                onClick = guidelinesButtonOnClick,
                modifier = Modifier
                    .align(Alignment.End)
                    .size(48.dp),
                shape = MaterialTheme.shapes.medium,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                containerColor = MaterialTheme.colorScheme.secondary,

                ) {
                Icon(
                    Icons.AutoMirrored.Filled.Help,
                    stringResource(R.string.description_content_button_cyclone_guidelines)
                )
            }

            Spacer(modifier = Modifier.size(8.dp))
        }

        ExtendedFloatingActionButton(
            expanded = expandedFab,
            onClick = namesButtonOnClick,
            icon = {
                Icon(
                    Icons.AutoMirrored.Filled.ListAlt,
                    stringResource(R.string.description_content_button_cyclone_names)
                )
            },
            text = { Text(text = stringResource(R.string.title_cyclone_names)) },
            shape = MaterialTheme.shapes.medium,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary
        )
    }
}


/**
 * SCREEN PREVIEWS
 **/
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Preview("Cyclone Warning Light Preview", showBackground = true)
@Preview(
    "Cyclone Warning Dark Preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ScreenWarningPreview() {

    val mockDataCycloneReport = CycloneReport(
        level = 4,
        next_bulletin = "12:50:00",
        news = listOf(
            "lorem ipsum dolor sit amet",
            "lorem ipsum dolor sit amet",
            "lorem ipsum dolor sit amet",
            "lorem ipsum dolor sit amet",
            "lorem ipsum dolor sit amet",
        )
    )
    MesTheme {
        CycloneReportDecisionsUi(
            cycloneReportUiState = CycloneReportUiState.Warning(mockDataCycloneReport),
            isRefreshing = false,
            animationSpeed = 1000,
            retryAction = {},
            ptrState = rememberPullToRefreshState(),
            showCycloneNamesOnClick = {},
            showCycloneGuidelinesOnClick = {}
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Preview("No Warning Light Preview", showBackground = true)
@Preview("No Warning Dark Preview", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ScreenNoWarningPreview() {

    MesTheme {
        CycloneReportDecisionsUi(
            cycloneReportUiState = CycloneReportUiState.NoWarning,
            isRefreshing = false,
            animationSpeed = 1000,
            retryAction = {},
            ptrState = rememberPullToRefreshState(),
            showCycloneNamesOnClick = {},
            showCycloneGuidelinesOnClick = {}
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Preview("Loading Light Preview", showBackground = true)
@Preview("Loading Dark Preview", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoadingCycloneReportScreenPreview() {
    MesTheme {
        CycloneReportDecisionsUi(
            cycloneReportUiState = CycloneReportUiState.Loading,
            isRefreshing = false,
            animationSpeed = 1000,
            retryAction = {},
            ptrState = rememberPullToRefreshState(),
            showCycloneNamesOnClick = {},
            showCycloneGuidelinesOnClick = {}
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Preview("Error Light Preview", showBackground = true)
@Preview("Error Dark Preview", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ErrorCycloneReportScreenPreview() {
    MesTheme {
        CycloneReportDecisionsUi(
            cycloneReportUiState = CycloneReportUiState.Error,
            isRefreshing = false,
            animationSpeed = 1000,
            retryAction = {},
            ptrState = rememberPullToRefreshState(),
            showCycloneNamesOnClick = {},
            showCycloneGuidelinesOnClick = {}
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Preview("Error Network Light Preview", showBackground = true)
@Preview(
    "Error Network Dark Preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ErrorNetworkCycloneReportScreenPreview() {
    MesTheme {
        CycloneReportDecisionsUi(
            cycloneReportUiState = CycloneReportUiState.NoNetwork,
            isRefreshing = false,
            animationSpeed = 1000,
            retryAction = {},
            ptrState = rememberPullToRefreshState(),
            showCycloneNamesOnClick = {},
            showCycloneGuidelinesOnClick = {}
        )
    }
}
