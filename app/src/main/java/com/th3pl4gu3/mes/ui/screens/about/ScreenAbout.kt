package com.th3pl4gu3.mes.ui.screens.about

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.models.AboutApp
import com.th3pl4gu3.mes.models.AboutInfo
import com.th3pl4gu3.mes.ui.components.MesAboutAppCard
import com.th3pl4gu3.mes.ui.components.MesAboutInfoCard
import com.th3pl4gu3.mes.ui.theme.MesTheme

@Composable
@ExperimentalMaterial3Api
fun ScreenAbout(
    scrollState: ScrollState = rememberScrollState()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
    ) {

        MesAboutAppCard(
            title = stringResource(id = R.string.section_about_title_about),
            aboutApp = AboutApp.designers
        )

        MesAboutInfoCard(
            title = stringResource(id = R.string.section_about_title_support_and_dev),
            aboutInfo = AboutInfo.supportAndDevelopment
        )

        MesAboutInfoCard(
            title = stringResource(id = R.string.section_about_title_other),
            aboutInfo = AboutInfo.other
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