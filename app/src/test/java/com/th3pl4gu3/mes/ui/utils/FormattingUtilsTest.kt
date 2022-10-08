package com.th3pl4gu3.mes.ui.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class FormattingUtilsTest {

    @ParameterizedTest
    @CsvSource(
        "1,1",
        "22,22",
        "333,333",
        "4444,4444",
        "55555,55555",
        "666666,666666",
        "7777777,777 7777",
        "88888888,8 888 8888",
    )
    fun toMruPhoneNumberString(number: Long, expectedResult: String?) {
        //Arrange
        val result: String?

        //Act
        result = number.toMruPhoneNumberString()

        //Assert
        assertEquals(expectedResult, result)
    }

    @ParameterizedTest
    @CsvSource(
        "1,1",
        "22,22",
        "333,333",
        "4444,4444",
        "55555,55555",
        "666666,666666",
        "777 7777,7777777",
        "8 888 8888,88888888",
    )
    fun toMruPhoneNumberInt(number: String, expectedResult: Long?) {
        //Arrange
        val result: Long?

        //Act
        result = number.toMruPhoneNumberLong()

        //Assert
        assertEquals(expectedResult, result)
    }
}