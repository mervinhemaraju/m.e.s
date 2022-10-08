package com.th3pl4gu3.mes.ui.utils

import java.lang.StringBuilder

fun Long.toMruPhoneNumberString(): String = with(StringBuilder(this.toString())) {

    if (this.length == 7) {
        this.insert(3, " ")

        return this.toString()
    }

    if (this.length == 8) {
        this.insert(1, " ")
        this.insert(5, " ")

        return this.toString()
    }

    return this.toString()
}

fun String.toMruPhoneNumberLong() = this.replace(" ", "").trim().toLong()