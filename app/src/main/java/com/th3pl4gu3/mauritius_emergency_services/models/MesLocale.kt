package com.th3pl4gu3.mauritius_emergency_services.models

sealed class MesLocale(
    val tag: String,
    val name: String
) {

    private object Default : MesLocale(
        "default",
        "English"
    )

    private object Fr : MesLocale(
        "fr",
        "Francais"
    )

    companion object {
        val Load = listOf(
            Default,
            Fr
        )
    }
}