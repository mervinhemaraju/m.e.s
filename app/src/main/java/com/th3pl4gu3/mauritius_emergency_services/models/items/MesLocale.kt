package com.th3pl4gu3.mauritius_emergency_services.models.items

sealed class MesLocale(
    val tag: String,
    val name: String
) {

    private data object Default : MesLocale(
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