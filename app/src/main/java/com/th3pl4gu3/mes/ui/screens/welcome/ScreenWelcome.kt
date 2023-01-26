package com.th3pl4gu3.mes.ui.screens.welcome

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.pager.*
import com.th3pl4gu3.mes.MainActivity
import com.th3pl4gu3.mes.models.WelcomeInfo
import com.th3pl4gu3.mes.ui.utils.GetRuntimePermissions
import com.th3pl4gu3.mes.ui.utils.HasNecessaryPermissions
import com.th3pl4gu3.mes.ui.utils.ShouldShowRationale
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
fun ScreenWelcome(
    unsetFirstTimeLogging: suspend () -> Unit
) {

    val context = LocalContext.current
    val activity = (context as? MainActivity)

    val coroutineScope = rememberCoroutineScope()
    val openDialog = remember { mutableStateOf(false) }
    val pagerState = rememberPagerState()
    val showRationale = remember { mutableStateOf(false) }
    val shouldShowRationale =
        if (activity?.ShouldShowRationale == null) false else activity.ShouldShowRationale

    ConstraintLayout(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(rememberContentPaddingForScreen())
    ) {

        val (
            pager,
            pagerIndicator,
            button,
            mainTitle,
            titleMes,
        ) = createRefs()

        Text(
            text = "Welcome to",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .constrainAs(mainTitle) {
                    top.linkTo(parent.top, 24.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                }
        )

        Text(
            text = "Mauritius Emergency Services",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .constrainAs(titleMes) {
                    top.linkTo(mainTitle.bottom, 24.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                },
            textAlign = TextAlign.Center
        )

        SliderPager(
            pagerState = pagerState,
            modifier = Modifier
                .constrainAs(pager) {
                    linkTo(
                        top = parent.top,
                        start = parent.start,
                        end = parent.end,
                        bottom = button.top,
                        topMargin = 24.dp
                    )
                }
        )

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .constrainAs(pagerIndicator) {
                    start.linkTo(pager.start)
                    end.linkTo(pager.end)
                    top.linkTo(pager.bottom, 16.dp)
                },
            activeColor = MaterialTheme.colorScheme.primary
        )

        Button(
            onClick = {
                coroutineScope.launch {
                    if (context.HasNecessaryPermissions) {
                        unsetFirstTimeLogging()
                    } else {
                        if (shouldShowRationale) {
                            openDialog(showRationale)
                        } else {
                            openDialog(openDialog)
                        }
                    }
                }
            },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .constrainAs(button) {
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom, 24.dp)
                }
        ) {
            Text(
                text = "Launch Mes"
            )
        }

    }

    if (showRationale.value) {
        PermissionDialog(
            title = "Enable Phone Call in Settings",
            description = "bla bla",
            confirmAction = {
                val intent = Intent().apply {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    addCategory(Intent.CATEGORY_DEFAULT)
                    data = Uri.parse("package:" + context.packageName)
                }
                closeDialog(showRationale)
                activity?.startActivity(intent)
            },
            denyAction = { closeDialog(showRationale) }
        )
    } else if (openDialog.value) {
        PermissionDialog(
            title = "Permissions needed for Notifications",
            description = "bla bla",
            confirmAction = {

                closeDialog(openDialog)

                activity?.requestMultiplePermissions?.launch(
                    GetRuntimePermissions
                )
            },
            denyAction = {
                closeDialog(openDialog)
            }
        )
    }
}

@Composable
@ExperimentalComposeUiApi
fun PermissionDialog(
    title: String,
    description: String,
    confirmAction: () -> Unit,
    denyAction: () -> Unit
) {
    AlertDialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = true
        ),
        title = {
            Text(
                text = title
            )
        },
        text = {
            Text(
                text = description
            )
        },
        onDismissRequest = denyAction,

        dismissButton = {
            Button(
                onClick = denyAction,
            ) {
                Text(text = "No")
            }
        },
        confirmButton = {
            Button(
                onClick = confirmAction,
            ) {
                Text(text = "Yes")
            }
        }
    )
}

@Composable
fun SliderPageBody(
    @DrawableRes image: Int,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {

    Spacer(
        modifier = Modifier.height(16.dp)
    )

    Image(
        painter = painterResource(id = image),
        contentDescription = description,
        modifier = modifier
            .clip(MaterialTheme.shapes.medium),
        contentScale = ContentScale.Crop
    )

    Spacer(
        modifier = Modifier.height(32.dp)
    )

    Text(
        text = title,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold
    )

    Spacer(
        modifier = Modifier.height(16.dp)
    )

    Text(
        text = description,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.primary
    )

    Spacer(
        modifier = Modifier.height(16.dp)
    )
}

@Composable
@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun SliderPager(
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    val pages = WelcomeInfo.pages

    HorizontalPager(
        count = pages.size,
        state = pagerState,
        modifier = modifier
            .background(Color.Transparent)
    ) { page ->

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier
                .padding(16.dp)
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                    // We animate the scaleX + scaleY, between 85% and 100%
                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }

                    // We animate the alpha, between 50% and 100%
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {
            Column(
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(16.dp)
            ) {
                SliderPageBody(
                    image = pages[page].image,
                    title = pages[page].title,
                    description = pages[page].description,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}


/**
 * Determine the content padding to apply to the different screens of the app
 */
@Composable
fun rememberContentPaddingForScreen(
    additionalTop: Dp = 0.dp,
    excludeTop: Boolean = false
) =
    WindowInsets.systemBars
        .only(if (excludeTop) WindowInsetsSides.Bottom else WindowInsetsSides.Vertical)
        .add(WindowInsets(top = additionalTop))
        .asPaddingValues()

private fun closeDialog(dialog: MutableState<Boolean>) {
    dialog.value = false
}

private fun openDialog(dialog: MutableState<Boolean>) {
    dialog.value = true
}

//@Preview("Starter Screen Light Preview", showBackground = true)
//@Preview(
//    "Starter Screen Dark Preview",
//    showBackground = true,
//    uiMode = Configuration.UI_MODE_NIGHT_YES
//)
//@Composable
//@ExperimentalPagerApi
//@ExperimentalFoundationApi
//@ExperimentalMaterial3Api
//@ExperimentalComposeUiApi
//@ExperimentalPermissionsApi
//fun StarterScreenPreview() {
//    MesTheme {
//        ScreenStarter(
//            application = MesApplication()
//        )
//    }
//}