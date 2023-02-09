package com.th3pl4gu3.mauritius_emergency_services.data

import com.th3pl4gu3.mauritius_emergency_services.models.Service

object DummyData {

    val services = listOf(
        Service(
            identifier = "id-01",
            name = "Police",
            type = "E",
            main_contact = 999,
            icon = "http://www.google.com",
            emails = listOf(),
            other_contacts = listOf()
        ),
        Service(
            identifier = "id-02",
            name = "SAMU",
            type = "E",
            main_contact = 999,
            icon = "http://www.google.com",
            emails = listOf(),
            other_contacts = listOf()

        ),
        Service(
            identifier = "id-03",
            name = "Hospital",
            type = "N",
            main_contact = 999,
            icon = "http://www.google.com",
            emails = listOf(),
            other_contacts = listOf()
        ),
    )
}