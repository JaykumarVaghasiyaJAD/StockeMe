package com.jay.stockeme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jay.stockeme.model.UserPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LocalViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    val loginStatus = userPreferences.loginStatus
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    val token = userPreferences.token
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun updateLoginStatus(isLoggedIn: Boolean) {
        viewModelScope.launch {
            userPreferences.saveLoginStatus(isLoggedIn)
        }
    }

    fun updateToken(newToken: String) {
        viewModelScope.launch {
            userPreferences.saveToken(newToken)
        }
    }

    fun clearLoginData() {
        viewModelScope.launch {
            userPreferences.clearLoginData()
        }
    }
}
