package com.jay.stockeme

object UseCases {
    fun validateEmail(email: String): String? {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return if (email.isEmpty()) {
            "Email cannot be empty"
        } else if (!email.matches(emailPattern.toRegex())) {
            "Invalid email format"
        } else {
            null
        }
    }
    fun validatePassword(password: String): String? {
        return if (password.length < 8) {
            "Password must be at least 8 characters long"
        } else {
            null
        }
    }
    fun validateUsername(userName: String): String? {
        return if (userName.length <= 3) {
            "Username must be at least 3 characters long"
        } else {
            null
        }
    }
}