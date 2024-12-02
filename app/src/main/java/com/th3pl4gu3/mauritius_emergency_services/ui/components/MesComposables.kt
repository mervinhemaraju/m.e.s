package com.th3pl4gu3.mauritius_emergency_services.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.models.items.AboutInfoDrawable
import com.th3pl4gu3.mauritius_emergency_services.models.items.AboutInfoVector
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.EmergencyButton
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme

@Composable
fun MesCounter(countdown: String, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = countdown,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, top = 12.dp)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp, bottom = 4.dp)
        )
    }
}

@Composable
fun MesIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    tint: Color = MaterialTheme.colorScheme.onSurface,
    contentDescription: String? = null,
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = tint,
        modifier = modifier
    )
}

@Composable
fun MesIcon(
    modifier: Modifier = Modifier,
    @DrawableRes painterResource: Int,
    @StringRes contentDescription: Int? = null,
    tint: Color = MaterialTheme.colorScheme.onSurface
) {
    Icon(
        painter = painterResource(id = painterResource),
        contentDescription = if (contentDescription != null) stringResource(id = contentDescription) else null,
        tint = tint,
        modifier = modifier
    )
}

@Composable
fun MesAsyncRoundedImage(
    modifier: Modifier = Modifier,
    service: Service,
    size: Dp = 64.dp
) {
    AsyncImage(
        modifier = modifier
            .size(size)
            .padding(8.dp)
            .clip(RoundedCornerShape(50)),
        contentScale = ContentScale.Crop,
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(service.icon)
            .crossfade(true)
            .build(),
        contentDescription = service.name,
        error = painterResource(R.drawable.ic_image_broken),
        placeholder = painterResource(R.drawable.ic_image_loading),
    )
}

@Composable
fun MesDrawerHeader(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        MesDrawerTitle(title = Pair("M", "auritius"))
        MesDrawerTitle(title = Pair("E", "mergency"))
        MesDrawerTitle(title = Pair("S", "ervices"))
    }
}

@Composable
fun MesDrawerTitle(title: Pair<String, String>) {
    Text(
        buildAnnotatedString {
            append(title.first)
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Medium
                )
            ) {
                append(title.second)
            }
        },
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        letterSpacing = 4.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
@ExperimentalFoundationApi
fun MesEmergencyButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.elevatedButtonColors(containerColor = EmergencyButton),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.outlineVariant),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp),
    ) {
        // Adding an Icon "Add" inside the Button
        MesIcon(
            painterResource = R.drawable.ic_emergency_beacons,
            contentDescription = R.string.ctnt_desc_emergency_button,
            tint = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
@ExperimentalMaterial3Api
fun MesAboutInfoCard(
    title: String,
    aboutInfo: List<AboutInfoVector>,
    onClick: (AboutInfoVector) -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Column {

            Text(
                text = title.uppercase(),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        bottom = 8.dp,
                    )
            )

            aboutInfo.forEach {
                MesAboutItem(
                    aboutInfo = it,
                    onClick = { onClick(it) }
                )
            }

        }
    }
}

@Composable
@ExperimentalMaterial3Api
fun MesAboutAppCard(
    title: String,
    aboutApp: List<AboutInfoDrawable>,
    onClick: (uri: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Column {

            Text(
                text = title.uppercase(),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        bottom = 8.dp,
                    )
            )

            Spacer(modifier = Modifier.height(16.dp))

            MesIcon(
                painterResource = R.drawable.ic_mes,
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.CenterHorizontally),
                contentDescription = R.string.app_name_short
            )

            Spacer(modifier = Modifier.height(32.dp))

            aboutApp.forEach {
                MesAboutItem(
                    aboutApp = it,
                    onClick = { onClick(it.uri) }
                )
            }

            Text(
                text = stringResource(id = R.string.app_development_sweet_message),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun MesTextButton(

    modifier: Modifier = Modifier,
    text: String,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor
        ),
        elevation = ButtonDefaults.buttonElevation(2.dp),
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = contentColor
        )
    }
}

@Composable
fun MesDataTable(data: List<List<String>>, header: List<String>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Header row
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(8.dp)
            ) {
                header.forEach { text ->
                    Box(
                        modifier = Modifier
                            .weight(1f) // This works because it's inside a Row
                            .padding(4.dp)
                    ) {
                        Text(
                            text = text,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Data rows
        items(data) { row ->
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp)
            ) {
                items(row) { cell ->
                    Box(
                        modifier = Modifier
                            .fillParentMaxWidth(1f / row.size) // Divide the width evenly across items
                            .padding(4.dp)
                    ) {
                        Text(
                            text = cell,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
@Preview(name = "Mes DataTable Light", showBackground = true)
@Preview(name = "Mes DataTable Dark", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
fun DataTablePreview() {
    val header = listOf("Name", "Age", "City")
    val data = listOf(
        listOf("Alice", "25", "New York"),
        listOf("Bob", "30", "San Francisco"),
        listOf("Charlie", "22", "Chicago")
    )
    MaterialTheme {
        MesDataTable(data = data, header = header)
    }
}

@Preview(name = "Mes Composable Light", showBackground = true)
@Preview(name = "Mes Composable Dark", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun MesComposablePreview() {

    val mockDataService = Service(
        identifier = "security-police-direct-2",
        name = "Samu",
        type = "E",
        icon = "https://img.icons8.com/fluent/100/000000/policeman-male.png",
        main_contact = 112,
        emails = listOf(),
        other_contacts = listOf()
    )

    val mockDataAboutInfoList = AboutInfoVector.supportAndDevelopment

    val mockDataAboutAppList = AboutInfoDrawable.developers

    MesTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            Arrangement.SpaceAround
        ) {

            MesCounter(
                countdown = "00",
                label = "min"
            )

            MesIcon(
                painterResource = R.drawable.ic_image_broken,
            )

            MesIcon(
                imageVector = Icons.Outlined.Phone,
            )

            MesTextButton(text = "Test", onClick = {})

            MesEmergencyButton(onClick = {}, modifier = Modifier.size(200.dp))

            MesDrawerHeader()

            MesDrawerTitle(title = Pair("T", "esting"))

            MesAboutInfoCard(
                title = "Title",
                aboutInfo = mockDataAboutInfoList,
                onClick = {}
            )

            MesAboutAppCard(
                title = "Title",
                aboutApp = mockDataAboutAppList,
                onClick = {}
            )
        }
    }
}
