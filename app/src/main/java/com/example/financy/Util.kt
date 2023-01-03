package com.example.financy

import kotlinx.android.synthetic.main.activity_new_transaction.*

object Util {
    /**
     * input is not valid if:
     * - email/password is empty
     * - passwords don't match
     * - passwords contains less than 8 chars
     * */
    fun validateRegistrationInput(
        email: String,
        password: String,
        confirmPassword: String)
            : Boolean {

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            return false
        }

        if (password != confirmPassword) {
            return false
        }

        if (password.length < 8) {
            return false
        }

        return true
    }

    fun formatDate(date:String):String {
        var array:List<String> = listOf()

        if(date.contains('-')) {
            array = date.split("-")
        }
        else if(date.contains(" ")) {
            array = date.split(" ")
        }

        return array.reversed().joinToString(separator = "/")
    }

}