package com.jay.stockeme.model

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Create a DataStore instance
val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

    // Define keys
    companion object {
        val LOGIN_STATUS_KEY = booleanPreferencesKey("login_status")
        val TOKEN_KEY = stringPreferencesKey("token")
    }

    // Function to save login status (true for logged in, false for logged out)
    suspend fun saveLoginStatus(isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[LOGIN_STATUS_KEY] = isLoggedIn
        }
    }

    // Function to save token
    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    // Function to clear login status and token
    suspend fun clearLoginData() {
        context.dataStore.edit { preferences ->
            preferences.remove(LOGIN_STATUS_KEY)
            preferences.remove(TOKEN_KEY)
        }
    }

    // Get login status (Flow<Boolean>)
    val loginStatus: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[LOGIN_STATUS_KEY] ?: false
        }

    // Get token (Flow<String?>)
    val token: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[TOKEN_KEY]
        }
}
