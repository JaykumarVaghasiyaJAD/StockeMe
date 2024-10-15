package com.jay.stockeme.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.jay.stockeme.Constant
import com.jay.stockeme.model.datamodel.User
import com.jay.stockeme.model.repository.AuthenticationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val userRegistrationRepository: AuthenticationRepository) : ViewModel() {

    private val _signUpStatus = MutableStateFlow<String?>(null)
    val signUpStatus: StateFlow<String?> = _signUpStatus


    private val _signInStatus = MutableStateFlow<String?>(null)
    val signInStatus: StateFlow<String?> = _signInStatus



    // Sign up method
    fun signUpWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            val result = userRegistrationRepository.signUpWithEmailAndPassword(email, password)
            result.fold(
                onSuccess = {
                    _signUpStatus.value = Constant.SIGN_UP_SUCCESS
                },
                onFailure = { exception ->
                    _signUpStatus.value = handleSignUpException(exception)
                }
            )
        }
    }


    // Handle specific sign-up exceptions
    private fun handleSignUpException(exception: Throwable): String {
        return when (exception) {
            is FirebaseAuthWeakPasswordException -> Constant.WEAK_PASSWORD_ERROR
            is FirebaseAuthInvalidCredentialsException -> Constant.INVALID_EMAIL_ERROR
            is FirebaseAuthUserCollisionException -> Constant.USER_ALREADY_EXISTS_ERROR
            else -> "${Constant.SIGN_UP_FAILED} ${exception.message}"
        }
    }

    // Sign in method
    fun signInWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            val result = userRegistrationRepository.signIn(email, password)
            result.fold(
                onSuccess = {
                    _signInStatus.value = Constant.SIGN_IN_SUCCESS
                },
                onFailure = { exception ->
                    _signInStatus.value = handleSignInException(exception)
                }
            )
        }
    }

    // Handle specific sign-in exceptions
    private fun handleSignInException(exception: Throwable): String {
        return when (exception) {
            is FirebaseAuthInvalidUserException -> Constant.USER_NOT_FOUND_ERROR
            is FirebaseAuthInvalidCredentialsException -> Constant.INVALID_CREDENTIALS_ERROR
            else -> "${Constant.SIGN_IN_FAILED} ${exception.message}"
        }
    }

}
