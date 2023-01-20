package com.th3pl4gu3.mes.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Space
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.models.*
import com.th3pl4gu3.mes.ui.theme.MesTheme
import com.th3pl4gu3.mes.ui.utils.capitalize

@Composable
fun MesIcon(
    imageVector: ImageVector,
    tint: Color = MaterialTheme.colorScheme.onSurface,
    @StringRes contentDescription: Int? = null,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = imageVector,
        contentDescription = if (contentDescription != null) stringResource(id = contentDescription) else null,
        tint = tint,
        modifier = modifier
    )
}

@Composable
fun MesIcon(
    @DrawableRes painterResource: Int,
    @StringRes contentDescription: Int? = null,
    tint: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(id = painterResource),
        contentDescription = if (contentDescription != null) stringResource(id = contentDescription) else null,
        tint = tint,
        modifier = modifier
    )
}

@Composable
fun MesAsyncRoundedImage(service: Service, size: Dp = 64.dp, modifier: Modifier = Modifier) {
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
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.primary,
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
        colors = ButtonDefaults.elevatedButtonColors(containerColor = MaterialTheme.colorScheme.secondary),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.outlineVariant),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp),
    ) {
        // Adding an Icon "Add" inside the Button
        MesIcon(
            painterResource = R.drawable.ic_emergency_beacons,
            contentDescription = R.string.ctnt_desc_emergency_button,
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
@ExperimentalMaterial3Api
fun MesAboutInfoCard(
    title: String,
    aboutInfo: List<AboutInfo>,
    modifier: Modifier = Modifier
) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(2.dp),
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
                    onClick = { }
                )
            }

        }
    }
}

@Composable
@ExperimentalMaterial3Api
fun MesAboutAppCard(
    title: String,
    aboutApp: List<AboutApp>,
    modifier: Modifier = Modifier
) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(2.dp),
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
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(32.dp))

            aboutApp.forEach {
                MesAboutItem(
                    aboutApp = it,
                    onClick = {}
                )
            }

            Text(
                text = "Developed with ❤ in \uD83C\uDDF2\uD83C\uDDFA",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
        }
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
        number = 112
    )

    val mockDataAboutInfoList = mutableListOf<AboutInfo>().apply {
        add(
            AboutInfo(
                icon = Icons.Outlined.Star,
                title = "Stars",
                description = "This is the testing part of the favorite sections"
            )
        )
        add(
            AboutInfo(
                icon = Icons.Outlined.Favorite,
                title = "Favorites",
                description = "This is the testing part of the favorite sections"
            )
        )
    }

    val mockDataAboutAppList = mutableListOf<AboutApp>().apply {
        add(
            AboutApp(
                icon = R.drawable.ic_lead_developer,
                title = "Developer",
                description = "This is the testing part of the favorite sections"
            )
        )
        add(
            AboutApp(
                icon = R.drawable.ic_graphic_designer,
                title = "Designer",
                description = "This is the testing part of the favorite sections"
            )
        )
    }

    MesTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            Arrangement.SpaceAround
        ) {

            MesIcon(
                painterResource = R.drawable.ic_image_broken,
            )

            MesIcon(
                imageVector = Icons.Outlined.Phone,
            )

            MesAsyncRoundedImage(service = mockDataService)

            MesEmergencyButton(onClick = {}, modifier = Modifier.size(200.dp))

            MesDrawerHeader()

            MesDrawerTitle(title = Pair("T", "esting"))

            MesAboutInfoCard(
                title = "Title",
                aboutInfo = mockDataAboutInfoList
            )

            MesAboutAppCard(
                title = "Title",
                aboutApp = mockDataAboutAppList
            )
        }
    }
}
