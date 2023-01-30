package com.th3pl4gu3.mes.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.th3pl4gu3.mes.R

sealed class WelcomeInfo(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int
) {
    private object First : WelcomeInfo(
        image = R.drawable.ic_ambulance,
        title = R.string.title_welcome_page_services_list,
        description = R.string.description_welcome_page_services_list,
    )

    private object Second : WelcomeInfo(
        image = R.drawable.ic_emergency,
        title = R.string.title_welcome_page_emergency_actions,
        description = R.string.description_welcome_page_emergency_actions,
    )

    private object Third : WelcomeInfo(
        image = R.drawable.ic_police_car,
        title = R.string.title_welcome_page_sos,
        description = R.string.description_welcome_page_sos,
    )

    private object Fourth : WelcomeInfo(
        image = R.drawable.ic_offline,
        title = R.string.title_welcome_page_offline_availability,
        description = R.string.description_welcome_page_offline_availability,
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