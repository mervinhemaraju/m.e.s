package com.th3pl4gu3.mes.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.th3pl4gu3.mes.BuildConfig
import com.th3pl4gu3.mes.R
import java.util.Objects

sealed class AboutInfoVector(
    val icon: ImageVector,
    @StringRes val title: Int,
    @StringRes val description: Int
){
    private object RateApp: AboutInfoVector(
        icon = Icons.Outlined.Grade,
        title = R.string.title_about_data_rate_mes,
        description = R.string.description_about_data_rate_mes
    )

    private object ShareApp: AboutInfoVector(
        icon = Icons.Outlined.Share,
        title = R.string.title_about_data_share_mes,
        description = R.string.description_about_data_share_mes
    )

    private object OpenSourceLicenses: AboutInfoVector(
        icon = Icons.Outlined.Copyright,
        title = R.string.title_about_data_licenses,
        description = R.string.description_about_data_licenses
    )

    private object Api: AboutInfoVector(
        icon = Icons.Outlined.Api,
        title = R.string.title_about_data_api,
        description = R.string.description_about_data_api
    )

    private object Version: AboutInfoVector(
        icon = Icons.Outlined.Info,
        title = R.string.title_about_version,
        description = R.string.title_about_version
    )

    companion object {
        val supportAndDevelopment = listOf(
            RateApp,
            ShareApp
        )
        val others = listOf(
            OpenSourceLicenses,
            Api,
            Version
        )
    }
}

sealed class AboutInfoDrawable(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    @StringRes val description: Int
){
    private object LeadDeveloper: AboutInfoDrawable(
        icon = R.drawable.ic_lead_developer,
        title = R.string.title_about_lead_developer,
        description = R.string.description_about_lead_developer
    )

    private object LeadDesigner: AboutInfoDrawable(
        icon = R.drawable.ic_graphic_designer,
        title = R.string.title_about_graphic_designer,
        description = R.string.description_about_graphic_designer
    )

    companion object {
        val developers = listOf(
            LeadDeveloper,
            LeadDesigner
        )
    }
}