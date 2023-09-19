package com.th3pl4gu3.mauritius_emergency_services.data

import com.th3pl4gu3.mauritius_emergency_services.models.Service

object DummyData {

    val services = listOf(
        Service(
            identifier = "id-01",
            name = "This is a super duper very freakishly long text",
            type = "E",
            main_contact = 999,
            icon = "http://www.google.com",
            emails = listOf(),
            other_contacts = listOf()
        ),
        Service(
            identifier = "id-02",
            name = "Shortest text",
            type = "E",
            main_contact = 999,
            icon = "http://www.google.com",
            emails = listOf(),
            other_contacts = listOf()

        ),
        Service(
            identifier = "id-03",
            name = "Normal to medium title",
            type = "N",
            main_contact = 999,
            icon = "http://www.google.com",
            emails = listOf(),
            other_contacts = listOf()
        ),
    )
}