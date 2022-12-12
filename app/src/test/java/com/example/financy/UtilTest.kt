package com.example.financy

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UtilTest {

    @Test
    fun testValidateRegistrationInput_EmptyMail() {
        val result = Util.validateRegistrationInput(
            "",
            "!password?",
            "!password?"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun testValidateRegistrationInput_EmptyPassword() {
        val result = Util.validateRegistrationInput(
            "test@sample.com",
            "",
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun testValidateRegistrationInput_InvalidPassword() {
        val result = Util.validateRegistrationInput(
            "test@sample.com",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun testValidateRegistrationInput_PasswordsNotMatching() {
        val result = Util.validateRegistrationInput(
            "test@sample.com",
            "password123",
            "password12"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun testValidateRegistrationInput_ValidInput() {
        val result = Util.validateRegistrationInput(
            "test@sample.com",
            "!password?",
            "!password?"
        )
        assertThat(result).isTrue();
    }

    @Test
    fun testFormatDate_1() {
        val result = Util.formatDate("2001-01-01")
        assertThat(result).isEqualTo("01/01/2001")
    }

    @Test
    fun testFormatDate_2() {
        val result = Util.formatDate("2022 12 05")
        assertThat(result).isEqualTo("05/12/2022")
    }
}