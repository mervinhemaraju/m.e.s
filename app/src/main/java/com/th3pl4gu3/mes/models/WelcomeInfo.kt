package com.th3pl4gu3.mes.models

import androidx.annotation.DrawableRes
import com.th3pl4gu3.mes.R

sealed class WelcomeInfo(
    @DrawableRes val image: Int,
    val title: String,
    val description: String
) {
    private object First : WelcomeInfo(
        image = R.drawable.im_mes_services_list,
        title = "Services List.",
        description = "Make calls directly from the services list by clicking on the phone on the left.",
    )

    private object Second : WelcomeInfo(
        image = R.drawable.im_mes_emergency_button,
        title = "Emergency Button.",
        description = "Make a direct call to your favorite emergency service by clicking on the gigantic red button.",
    )

    private object Third : WelcomeInfo(
        image = R.drawable.im_mes_emergency_actions,
        title = "Emergency Actions",
        description = "Make calls to your emergency services right from the home screen.",
    )

    private object Fourth : WelcomeInfo(
        image = R.drawable.im_mes_theme,
        title = "Enjoy the Experience",
        description = "MES has been designed using Minimalistic design so that it is usable by everyone.",
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