package com.th3pl4gu3.mauritius_emergency_services.ui.navigation

import com.th3pl4gu3.mauritius_emergency_services.ui.MesApp
import com.th3pl4gu3.mauritius_emergency_services.utils.KEYWORD_IS_EMERGENCY_ARGUMENT
import com.th3pl4gu3.mauritius_emergency_services.utils.KEYWORD_SERVICE_IDENTIFIER_ARGUMENT
import com.th3pl4gu3.mauritius_emergency_services.utils.KEYWORD_SERVICE_NUMBER_ARGUMENT


/**
 * Arguments placeholders used in [MesDestinations].
 */
object MesDestinationArguments {
    const val ARG_PRE_CALL_SERVICE_IDENTIFIER = "{$KEYWORD_SERVICE_IDENTIFIER_ARGUMENT}"
    const val ARG_PRE_CALL_SERVICE_NUMBER = "{$KEYWORD_SERVICE_NUMBER_ARGUMENT}"
    const val ARG_PRE_CALL_IS_EMERGENCY = "{$KEYWORD_IS_EMERGENCY_ARGUMENT}"
}

/**
 * Destinations used in the [MesApp].
 */
object MesDestinations {
    const val SCREEN_WELCOME = "welcome"
    const val SCREEN_HOME = "home"
    const val SCREEN_SERVICES = "services"
    const val SCREEN_CYCLONE_REPORT = "cyclone_report"
    const val SCREEN_ABOUT = "about"
    const val SCREEN_SETTINGS = "settings"
    const val SCREEN_THEME = "theme"
    const val SCREEN_CONTACTUS = "contactus"
    const val SCREEN_PRE_CALL =
        "pre_call/${MesDestinationArguments.ARG_PRE_CALL_SERVICE_IDENTIFIER}/${MesDestinationArguments.ARG_PRE_CALL_SERVICE_NUMBER}/${MesDestinationArguments.ARG_PRE_CALL_IS_EMERGENCY}"
}