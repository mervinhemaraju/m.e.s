package com.th3pl4gu3.mauritius_emergency_services.ui.screens.welcome

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import android.provider.Settings
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.*
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.activity.MesActivity
import com.th3pl4gu3.mauritius_emergency_services.models.items.WelcomeInfo
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesIcon
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesTextButton
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesTwoActionDialog
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.GetRuntimePermissions
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.HasNecessaryPermissions
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.ShouldShowRationale
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme
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
    val activity = (context as? MesActivity)

    val coroutineScope = rememberCoroutineScope()
    val openDialog = remember { mutableStateOf(false) }
    val pagerState = rememberPagerState()
    val showRationale = remember { mutableStateOf(false) }
    val shouldShowRationale =
        if (activity?.ShouldShowRationale == null) false else activity.ShouldShowRationale

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.headline_welcome_primary),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                )
        )

        Text(
            text = stringResource(id = R.string.app_name_long),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        SliderPager(
            pagerState = pagerState,
        )

        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.weight(1f))

        MesTextButton(
            text = stringResource(id = R.string.action_launch_mes),
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
            modifier = Modifier
                .padding(16.dp)
        )

    }

    if (showRationale.value) {
        PermissionDialog(
            title = stringResource(id = R.string.title_welcome_mes_permissions_rationale_dialog),
            description = stringResource(id = R.string.description_welcome_mes_permissions_rationale_dialog),
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
            title = stringResource(id = R.string.title_welcome_mes_permissions_needed_dialog),
            description = stringResource(id = R.string.description_welcome_mes_permissions_needed_dialog),
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
@ExperimentalMaterial3Api
fun PermissionDialog(
    title: String,
    description: String,
    confirmAction: () -> Unit,
    denyAction: () -> Unit
) {
    MesTwoActionDialog(
        title = title,
        onDismissRequest = denyAction,
        content = {
            Text(
                text = description,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        confirmButtonAction = confirmAction,
        confirmButtonLabel = stringResource(id = R.string.action_proceed),
        denyButtonLabel = stringResource(id = R.string.action_cancel),
        denyButtonAction = denyAction
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

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(56.dp)
    ) {
        MesIcon(
            painterResource = image,
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.Center),
            tint = MaterialTheme.colorScheme.primary
        )
    }
    Spacer(
        modifier = Modifier.height(48.dp)
    )

    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = modifier
    )

    Spacer(
        modifier = Modifier.height(16.dp)
    )

    Text(
        text = description,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Center,
        modifier = modifier
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
                containerColor = Color.Transparent
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
                    title = stringResource(id = pages[page].title),
                    description = stringResource(id = pages[page].description),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
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

@Preview("Starter Screen Light Preview", showBackground = true)
@Preview(
    "Starter Screen Dark Preview",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
fun WelcomeScreenPreview() {
    MesTheme {
        ScreenWelcome(
            unsetFirstTimeLogging = {}
        )
    }
}

@Preview("Starter Screen Permission Dialog Light Preview", showBackground = true)
@Preview(
    "Starter Screen Permission Dialog Dark Preview",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
fun WelcomeScreenPermissionDialogPreview() {
    MesTheme {
        PermissionDialog(
            title = "This is a title",
            description = "This is a description",
            confirmAction = {},
            denyAction = {}
        )
    }
}