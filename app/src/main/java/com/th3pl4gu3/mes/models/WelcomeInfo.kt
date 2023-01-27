package com.th3pl4gu3.mes.models

import androidx.annotation.DrawableRes
import com.th3pl4gu3.mes.R

sealed class WelcomeInfo(
    @DrawableRes val image: Int,
    val title: String,
    val description: String
) {
    private object First : WelcomeInfo(
        image = R.drawable.ic_ambulance,
        title = "Services List",
        description = "Get a list of all emergency and non-emergency contacts right from your phone.",
    )

    private object Second : WelcomeInfo(
        image = R.drawable.ic_emergency,
        title = "Emergency Actions",
        description = "Get a dedicated corner on your dashboard just for emergency services",
    )

    private object Third : WelcomeInfo(
        image = R.drawable.ic_police_car,
        title = "SOS",
        description = "Call for urgent help right from the app using the big red button",
    )

    private object Fourth : WelcomeInfo(
        image = R.drawable.ic_offline,
        title = "Offline",
        description = "Get all your services available to you offline. You just need to be connected online once for MES to download the services.",
    )

    companion object {
        val pages: List<WelcomeInfo>
            get() = listOf(
                First,
                Second,
                Third,
                Fourth
            )
    }
}