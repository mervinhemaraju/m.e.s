package com.th3pl4gu3.mauritius_emergency_services.ui.screens.cyclone_report

import android.content.res.Configuration
import android.view.Display.Mode
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesIcon
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme
import kotlinx.coroutines.delay

@Composable
fun ScreenCycloneReport(
//    cycloneReportViewModel: CycloneReportViewModel
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

@Composable
fun ScreenWarning(news: List<String>) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing)
        ), label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            MesIcon(
                painterResource = R.drawable.ic_cyclone,
//                contentDescription = "Cyclone",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .weight(1.5f)
                    .graphicsLayer {
                        rotationZ = angle
                    }
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "Current class of",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )

                Text(
                    text = "4",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 112.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                )

            }
        }


        Text(
            text = "Safety Measures",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Column(
                modifier = Modifier
                    .weight(2f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(16.dp)
                )
                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(16.dp)
                )

            }

            MesIcon(
                painterResource = R.drawable.ic_safety,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .weight(1f)
                    .size(96.dp)
                    .align(Alignment.CenterVertically)
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(32.dp)
        )

        Text(
            text = "Next Bulletin",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        Text(
            text = "00 : 00 : 00",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            fontSize = 56.sp,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )


        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(32.dp)
        )

        Text(
            text = "Latest News",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 240.dp),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                news,
            ) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    lineHeight = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            MaterialTheme.shapes.extraSmall
                        )
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .padding(
                            top = 8.dp,
                            bottom = 8.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                )
            }
        }
    }
}

//@Composable
//fun ScreenWarning(
//    modifier: Modifier = Modifier,
//    news: List<String>
//) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background)
//    ) {
//        Text(
//            text = "Mauritius is in a warning level of",
//            style = MaterialTheme.typography.labelMedium,
//            color = MaterialTheme.colorScheme.onBackground,
//            modifier = Modifier
//                .padding(16.dp)
//        )
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        Text(
//            text = "4",
//            style = MaterialTheme.typography.titleLarge,
//            textAlign = TextAlign.Center,
//            color = Color(0xFFD50000),
//            fontWeight = FontWeight.Bold,
//            fontSize = 104.sp,
//            modifier = Modifier
//                .background(
//                    Color(0xFFD50000).copy(alpha = 0.1f),
//                    RoundedCornerShape(50)
//                )
//                .circleLayout()
//                .padding(54.dp)
//        )
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        Text(
//            text = "The next bulletin is at 00:00:00 PM",
//            style = MaterialTheme.typography.bodyMedium,
//            color = MaterialTheme.colorScheme.onBackground,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .padding(16.dp)
//        )
//
//        Text(
//            text = "Cyclone News",
//            style = MaterialTheme.typography.labelMedium,
//            color = MaterialTheme.colorScheme.primary,
//            modifier = Modifier
//                .padding(12.dp)
//        )
//
//        CycloneNews(
//            news = news
//        )
//    }
//}
//
//@Composable
//fun CycloneNews(
//    modifier: Modifier = Modifier,
//    news: List<String>
//) {
//
//    LazyVerticalGrid(
//        columns = GridCells.Adaptive(minSize = 240.dp),
//        contentPadding = PaddingValues(8.dp),
//        verticalArrangement = Arrangement.spacedBy(10.dp),
//        horizontalArrangement = Arrangement.spacedBy(10.dp),
//        modifier = modifier
//    ) {
//        items(
//            news,
//        ) {
//            Text(
//                text = it,
//                style = MaterialTheme.typography.bodySmall,
//                color = MaterialTheme.colorScheme.secondary,
//                lineHeight = 20.sp,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .clip(
//                        MaterialTheme.shapes.extraSmall
//                    )
//                    .shadow(
//                        0.7.dp,
//                        shape = MaterialTheme.shapes.extraSmall
//                    )
//                    .padding(
//                        top = 8.dp,
//                        bottom = 8.dp,
//                        start = 16.dp,
//                        end = 16.dp
//                    )
//            )
//        }
//    }
//}

@Preview("Warning Light Preview", showBackground = true)
@Preview("Warning Dark Preview", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WarningCycloneReportScreenPreview() {
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
fun NoWarningCycloneReportScreenPreview() {
    MesTheme {
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