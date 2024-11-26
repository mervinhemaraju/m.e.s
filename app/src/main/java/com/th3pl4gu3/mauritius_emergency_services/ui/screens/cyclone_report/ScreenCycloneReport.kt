package com.th3pl4gu3.mauritius_emergency_services.ui.screens.cyclone_report

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CallMade
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.PhoneMissed
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesCounter
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesIcon
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesScreenError
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesScreenLoading
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesScreenNoContent
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.services.ServicesList
import com.th3pl4gu3.mauritius_emergency_services.ui.screens.services.ServicesUiState
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme

@Composable
fun ScreenCycloneReport(
) {
    val mockData = listOf(
        "Un avertissement de cyclone de Classe 3 est en vigueur a Maurice.   | Quinzieme et dernier bulletin de cyclone pour Rodrigues emis a 1010 heures ce lundi 20 fevrier 2023.",
        "Mon, Feb 20, 2023A cyclone warning class III is in force in Mauritius. A cyclone warning class III is in force in Mauritius.Eleventh cyclone bulletin for Mauritius issued at 1610 hours on Monday 20 February 2023.At 1600 hours, intense tropical cyclone Freddy was located at about 120 km almost to the north of Grand Bay, in latitude 18.9 degrees south and longitude 57.0 degrees east. The estimated central pressure of Freddy is around 930 hectopascals and the estimated wind gusts near its centre are about 280 km/h. It is moving towards the west south west at a speed of about 30 km/h.A slight recurvature in the trajectory towards the south may still bring the centre of Freddy closer to Mauritius. A further deterioration in the weather is expected in the coming hours and cyclonic conditions may still prevail.A cyclone warning class III is maintained in Mauritius.The public in Mauritius is advised to maintain all precautions and to stay in safe places.Weather will be overcast with scattered showers, moderate to heavy at times, with thunderstorms.Wind will blow from the east at a speed of about 60 km/h, strengthening gradually with gusts reaching 120 km/h in the evening.Sea will be phenomenal with heavy swells of the order of 7 metres beyond the reefs. Storm surge will continue to cause inundation along the low-lying coastal areas. It is, therefore, strictly advised not to go at sea.A cyclone warning class III is in force in Mauritius. A cyclone warning class III is in force in Mauritius.The next bulletin will be issued at around 1910 hours.",
        "A cyclone warning class III is in force in Mauritius.",
        "A cyclone warning class III is in force in Mauritius.",
        "Eleventh cyclone bulletin for Mauritius issued at 1610 hours on Monday 20 February 2023.",
        "At 1600 hours, intense tropical cyclone Freddy was located at about 120 km almost to the north of Grand Bay, in latitude 18.9 degrees south and longitude 57.0 degrees east.",
        "The estimated central pressure of Freddy is around 930 hectopascals and the estimated wind gusts near its centre are about 280 km/h. It is moving towards the west south west at a speed of about 30 km/h.",
        "A slight recurvature in the trajectory towards the south may still bring the centre of Freddy closer to Mauritius. A further deterioration in the weather is expected in the coming hours and cyclonic conditions may still prevail.",
        "A cyclone warning class III is maintained in Mauritius.",
        "The public in Mauritius is advised to maintain all precautions and to stay in safe places.",
        "Weather will be overcast with scattered showers, moderate to heavy at times, with thunderstorms.",
        "Wind will blow from the east at a speed of about 60 km/h, strengthening gradually with gusts reaching 120 km/h in the evening.",
        "Sea will be phenomenal with heavy swells of the order of 7 metres beyond the reefs. Storm surge will continue to cause inundation along the low-lying coastal areas. It is, therefore, strictly advised not to go at sea.",
        "A cyclone warning class III is in force in Mauritius.",
        "A cyclone warning class III is in force in Mauritius.",
        "The next bulletin will be issued at around 1910 hours."
    )

    ScreenWarning(mockData)
}

/**
 * The decision make for Ui State
 **/
@Composable
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun ReportUiStateDecisions(
    cycloneReportUiState: CycloneReportUiState,
    retryAction: () -> Unit,
//    navigateToPreCall: (service: Service, chosenNumber: String) -> Unit,
//    listState: LazyListState,
//    modifier: Modifier = Modifier
) {
    when (cycloneReportUiState) {
        is CycloneReportUiState.Loading -> MesScreenLoading(
            // FIXME(Update loading text)
            loadingMessage = stringResource(id = R.string.message_loading_services),
            modifier = Modifier
        )
        is CycloneReportUiState.Warning -> {}
        is CycloneReportUiState.NoWarning -> {}
        is CycloneReportUiState.Error -> MesScreenError(
            retryAction = retryAction,
            // FIXME(Update error text)
            errorMessage = stringResource(id = R.string.message_error_loading_services_failed),
            modifier = Modifier
        )
    }
}

/** Screens **/
@Composable
fun ScreenWarning(
    news: List<String>,
    scrollState: ScrollState = rememberScrollState()
) {

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing)
        ), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .verticalScroll(scrollState)
        ) {

            Text(
                text = "Mauritius is currently in",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 4.dp)
            )

            Text(
                text = "Class 4",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.size(34.dp))

            MesIcon(
                painterResource = R.drawable.ic_cyclone,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .graphicsLayer {
                        rotationZ = angle
                    }
            )

            Spacer(modifier = Modifier.size(48.dp))

            WidgetNextBulletin()

            Spacer(modifier = Modifier.size(48.dp))

            WidgetLatestNews(news = news)
        }

        WidgetActionButtons(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun ScreenNoWarning() {
    Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {

        Row {
            MesIcon(
                painterResource = R.drawable.ic_cloud,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
            )
            MesIcon(
                painterResource = R.drawable.ic_cloud,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
            )
        }

        Text(
            text = "There are no cyclone warnings in Mauritius at the moment.",
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
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}

/** Widgets **/
@Composable
fun WidgetLatestNews(news: List<String>) {
    Text(
        text = "Latest News",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .fillMaxWidth()
    )

    LazyRow(
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp),
        modifier = Modifier
            .padding(bottom = 80.dp)
    ) {
        items(news) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.outlinedCardElevation(
                    0.7.dp
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
fun WidgetNextBulletin() {

    val backgroundThemeModifier = Modifier
        .clip(MaterialTheme.shapes.large)
        .background(MaterialTheme.colorScheme.surfaceContainerLow)

    Text(
        text = "Next Bulletin",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    )

    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {

        MesCounter(
            countdown = "00",
            label = "hr",
            modifier = backgroundThemeModifier
        )

        Text(
            text = ":",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(4.dp, 0.dp)
                .align(Alignment.CenterVertically)
        )

        MesCounter(
            countdown = "00",
            label = "min",
            modifier = backgroundThemeModifier
        )

        Text(
            text = ":",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(4.dp, 0.dp)
                .align(Alignment.CenterVertically)
        )

        MesCounter(
            countdown = "00",
            label = "s",
            modifier = backgroundThemeModifier
        )
    }
}

@Composable
fun WidgetActionButtons(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = { /* doSomething() */ },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .size(52.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Localized description")
        }

        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            ),
            modifier = Modifier
                .weight(3f)
                .height(50.dp)
        ) {
            Icon(
                Icons.Outlined.Phone,
                contentDescription = "Localized description",
                modifier = Modifier.padding(end = 8.dp)
            )

            Text("Filled")
        }

        IconButton(
            onClick = { /* doSomething() */ },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .size(52.dp)
        ) {
            Icon(Icons.Outlined.Info, contentDescription = "Localized description")
        }
    }
}

@Preview("Warning Light Preview", showBackground = true)
@Preview("Warning Dark Preview", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ScreenWarningPreview() {
    val mockData = listOf(
        "Un avertissement de cyclone de Classe 3 est en vigueur a Maurice.   | Quinzieme et dernier bulletin de cyclone pour Rodrigues emis a 1010 heures ce lundi 20 fevrier 2023.",
        "Mon, Feb 20, 2023A cyclone warning class III is in force in Mauritius. A cyclone warning class III is in force in Mauritius.Eleventh cyclone bulletin for Mauritius issued at 1610 hours on Monday 20 February 2023.At 1600 hours, intense tropical cyclone Freddy was located at about 120 km almost to the north of Grand Bay, in latitude 18.9 degrees south and longitude 57.0 degrees east. The estimated central pressure of Freddy is around 930 hectopascals and the estimated wind gusts near its centre are about 280 km/h. It is moving towards the west south west at a speed of about 30 km/h.A slight recurvature in the trajectory towards the south may still bring the centre of Freddy closer to Mauritius. A further deterioration in the weather is expected in the coming hours and cyclonic conditions may still prevail.A cyclone warning class III is maintained in Mauritius.The public in Mauritius is advised to maintain all precautions and to stay in safe places.Weather will be overcast with scattered showers, moderate to heavy at times, with thunderstorms.Wind will blow from the east at a speed of about 60 km/h, strengthening gradually with gusts reaching 120 km/h in the evening.Sea will be phenomenal with heavy swells of the order of 7 metres beyond the reefs. Storm surge will continue to cause inundation along the low-lying coastal areas. It is, therefore, strictly advised not to go at sea.A cyclone warning class III is in force in Mauritius. A cyclone warning class III is in force in Mauritius.The next bulletin will be issued at around 1910 hours.",
        "A cyclone warning class III is in force in Mauritius.",
        "A cyclone warning class III is in force in Mauritius.",
        "Eleventh cyclone bulletin for Mauritius issued at 1610 hours on Monday 20 February 2023.",
        "At 1600 hours, intense tropical cyclone Freddy was located at about 120 km almost to the north of Grand Bay, in latitude 18.9 degrees south and longitude 57.0 degrees east.",
        "The estimated central pressure of Freddy is around 930 hectopascals and the estimated wind gusts near its centre are about 280 km/h. It is moving towards the west south west at a speed of about 30 km/h.",
        "A slight recurvature in the trajectory towards the south may still bring the centre of Freddy closer to Mauritius. A further deterioration in the weather is expected in the coming hours and cyclonic conditions may still prevail.",
        "A cyclone warning class III is maintained in Mauritius.",
        "The public in Mauritius is advised to maintain all precautions and to stay in safe places.",
        "Weather will be overcast with scattered showers, moderate to heavy at times, with thunderstorms.",
        "Wind will blow from the east at a speed of about 60 km/h, strengthening gradually with gusts reaching 120 km/h in the evening.",
        "Sea will be phenomenal with heavy swells of the order of 7 metres beyond the reefs. Storm surge will continue to cause inundation along the low-lying coastal areas. It is, therefore, strictly advised not to go at sea.",
        "A cyclone warning class III is in force in Mauritius.",
        "A cyclone warning class III is in force in Mauritius.",
        "The next bulletin will be issued at around 1910 hours."
    )

    MesTheme {
        ScreenWarning(
            news = mockData
        )
    }
}

@Preview("No Warning Light Preview", showBackground = true)
@Preview("No Warning Dark Preview", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ScreenNoWarningPreview() {
    MesTheme {
        ScreenNoWarning()
    }
}

@Preview("Loading Light Preview", showBackground = true)
@Preview("Loading Dark Preview", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoadingCycloneReportScreenPreview() {
    MesTheme {
    }
}

@Preview("Error Light Preview", showBackground = true)
@Preview("Error Dark Preview", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ErrorCycloneReportScreenPreview() {
    MesTheme {
    }
}