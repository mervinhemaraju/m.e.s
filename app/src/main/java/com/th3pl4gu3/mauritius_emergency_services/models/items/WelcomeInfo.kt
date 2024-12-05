package com.th3pl4gu3.mauritius_emergency_services.models.items

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.th3pl4gu3.mauritius_emergency_services.R

sealed class WelcomeInfo(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int
) {
    private data object CycloneReport : WelcomeInfo(
        image = R.drawable.ic_cyclone,
        title = R.string.title_welcome_page_cyclone_report,
        description = R.string.description_welcome_page_cyclone_report,
    )
    private data object ServicesList : WelcomeInfo(
        image = R.drawable.ic_ambulance,
        title = R.string.title_welcome_page_services_list,
        description = R.string.description_welcome_page_services_list,
    )

    private data object EmergencyActions : WelcomeInfo(
        image = R.drawable.ic_emergency,
        title = R.string.title_welcome_page_emergency_actions,
        description = R.string.description_welcome_page_emergency_actions,
    )

    private data object Sos : WelcomeInfo(
        image = R.drawable.ic_police_car,
        title = R.string.title_welcome_page_sos,
        description = R.string.description_welcome_page_sos,
    )

    private data object OfflineAvailability : WelcomeInfo(
        image = R.drawable.ic_offline,
        title = R.string.title_welcome_page_offline_availability,
        description = R.string.description_welcome_page_offline_availability,
    )

    companion object {
        val pages: List<WelcomeInfo>
            get() = listOf(
                ServicesList,
                CycloneReport,
                EmergencyActions,
                Sos,
                OfflineAvailability
            )
    }
}