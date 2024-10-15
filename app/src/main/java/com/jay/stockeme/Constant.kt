package com.jay.stockeme

object Constant {
    const val API_KEY = "f5c567e8a32667270dd61c189076932a57573a43"
    const val BASE_URL = "https://api.tiingo.com"
    const val SEARCH_DELAY_MS = 300L
    const val SEARCH_LIMIT = 100
    const val EXPIRATION_TIME_MS = 86400000L // 24 hours

    const val SIGN_UP_SUCCESS = "Sign up successful!"
    const val SIGN_UP_FAILED = "Sign up failed."
    const val SIGN_IN_SUCCESS = "Sign in successful!"
    const val SIGN_IN_FAILED = "Sign in failed."

    const val WEAK_PASSWORD_ERROR = "The password is too weak."
    const val INVALID_EMAIL_ERROR = "The email format is invalid."
    const val USER_ALREADY_EXISTS_ERROR = "This email is already registered."
    const val USER_NOT_FOUND_ERROR = "No account found with this email."
    const val INVALID_CREDENTIALS_ERROR = "Invalid email or password."
    const val UNABLE_TO_GET_ID = "Unable to retrieve user ID."
}