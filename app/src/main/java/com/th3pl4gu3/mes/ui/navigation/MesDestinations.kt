package com.th3pl4gu3.mes.ui.navigation

import com.th3pl4gu3.mes.ui.MesApp
import com.th3pl4gu3.mes.ui.utils.KEYWORD_SERVICE_IDENTIFIER_ARGUMENT


/**
 * Arguments placeholders used in [MesDestinations].
 */
object MesDestinationArguments {
    const val ARG_PRE_CALL_SERVICE_IDENTIFIER = "{$KEYWORD_SERVICE_IDENTIFIER_ARGUMENT}"
}

/**
 * Destinations used in the [MesApp].
 */
object MesDestinations {
    const val SCREEN_WELCOME = "welcome"
    const val SCREEN_HOME = "home"
    const val SCREEN_SERVICES = "services"
    const val SCREEN_ABOUT = "about"
    const val SCREEN_SETTINGS = "settings"
    const val SCREEN_THEME = "theme"
    const val SCREEN_CONTACTUS = "contactus"
    const val SCREEN_PRE_CALL = "pre_call/${MesDestinationArguments.ARG_PRE_CALL_SERVICE_IDENTIFIER}"
}