package com.th3pl4gu3.mes.models

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.th3pl4gu3.mes.BuildConfig
import com.th3pl4gu3.mes.R

sealed class AboutData(
    open val title: String,
    open val description: String
)

data class AboutInfo(
    val icon: ImageVector,
    override val title: String,
    override val description: String
) : AboutData(title, description) {
    companion object {
        val supportAndDevelopment: MutableList<AboutInfo> = mutableListOf<AboutInfo>().apply {
            add(
                AboutInfo(
                    icon = Icons.Outlined.Grade,
                    title = "Rate Mes",
                    description = "If you love the app, let us know in the Google Play Store and we can make it even better."
                )
            )
            add(
                AboutInfo(
                    icon = Icons.Outlined.BugReport,
                    title = "Report a Bug",
                    description = "Any issues that you came across while navigating MES, please let us know."
                )
            )
            add(
                AboutInfo(
                    icon = Icons.Outlined.Share,
                    title = "Share Mes",
                    description = "Do not forget to share Mes with your friends and families."
                )
            )
        }

        val other: MutableList<AboutInfo> = mutableListOf<AboutInfo>().apply {
            add(
                AboutInfo(
                    icon = Icons.Outlined.Copyright,
                    title = "Open Source Licenses",
                    description = "Open Source Licenses"
                )
            )
            add(
                AboutInfo(
                    icon = Icons.Outlined.Api,
                    title = "Developer API",
                    description = "Application Program Interface (API) used in MES"
                )
            )
            add(
                AboutInfo(
                    icon = Icons.Outlined.Info,
                    title = "Version",
                    description = BuildConfig.VERSION_CODE.toString()
                )
            )
        }
    }
}

data class AboutApp(
    @DrawableRes val icon: Int,
    override val title: String,
    override val description: String
) : AboutData(title, description){

    companion object {
        val designers: MutableList<AboutApp> = mutableListOf<AboutApp>().apply {
            add(
                AboutApp(
                    icon = R.drawable.ic_lead_developer,
                    title = "Mervin Hemaraju",
                    description = "Lead Developer & Designer"
                )
            )
            add(
                AboutApp(
                    icon = R.drawable.ic_graphic_designer,
                    title = "Nick Foo Kune",
                    description = "Play Store Banner & Images"
                )
            )
        }
    }
}
