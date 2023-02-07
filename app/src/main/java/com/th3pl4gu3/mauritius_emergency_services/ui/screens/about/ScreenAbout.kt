package com.th3pl4gu3.mauritius_emergency_services.ui.screens.about

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.activity.MesActivity
import com.th3pl4gu3.mauritius_emergency_services.models.AboutInfoDrawable
import com.th3pl4gu3.mauritius_emergency_services.models.AboutInfoVector
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesAboutAppCard
import com.th3pl4gu3.mauritius_emergency_services.ui.components.MesAboutInfoCard
import com.th3pl4gu3.mauritius_emergency_services.ui.theme.MesTheme
import com.th3pl4gu3.mauritius_emergency_services.utils.URI_APP_PLAY_STORE
import com.th3pl4gu3.mauritius_emergency_services.utils.URI_MES_API
import com.th3pl4gu3.mauritius_emergency_services.utils.URI_MES_WEBSITE
import com.th3pl4gu3.mauritius_emergency_services.utils.URI_MES_WEBSITE_PRIVACY

@Composable
@ExperimentalMaterial3Api
fun ScreenAbout(
    scrollState: ScrollState = rememberScrollState()
) {

    val localUriHandler = LocalUriHandler.current
    val activity = LocalContext.current as MesActivity
    val aboutInfoVectorOnClick: (AboutInfoVector) -> Unit = {
        when (it) {
            AboutInfoVector.RateApp -> {
                localUriHandler.openUri(URI_APP_PLAY_STORE)
            }
            AboutInfoVector.Api -> {
                localUriHandler.openUri(URI_MES_API)
            }
            AboutInfoVector.AboutUs -> {
                localUriHandler.openUri(URI_MES_WEBSITE)
            }
            AboutInfoVector.PrivacyPolicy -> {
                localUriHandler.openUri(URI_MES_WEBSITE_PRIVACY)
            }
            AboutInfoVector.ShareApp -> {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, URI_APP_PLAY_STORE)
                    type = "text/plain"

                    Intent.createChooser(this@apply, null)
                }

                activity.startActivity(intent)
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
    ) {

        MesAboutAppCard(
            title = stringResource(id = R.string.section_about_title_about),
            aboutApp = AboutInfoDrawable.developers,
            onClick = {
                localUriHandler.openUri(it)
            }
        )

        MesAboutInfoCard(
            title = stringResource(id = R.string.section_about_title_support_and_dev),
            aboutInfo = AboutInfoVector.supportAndDevelopment,
            onClick = aboutInfoVectorOnClick
        )

        MesAboutInfoCard(
            title = stringResource(id = R.string.section_about_title_other),
            aboutInfo = AboutInfoVector.others,
            onClick = aboutInfoVectorOnClick
        )

    }
}


@Preview("Home Screen Light")
@Preview("Home Screen Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
fun ScreenHomePreview() {
    MesTheme {
        ScreenAbout()
    }
}