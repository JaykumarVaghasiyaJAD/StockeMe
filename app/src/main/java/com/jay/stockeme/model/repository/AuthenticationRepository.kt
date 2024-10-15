package com.jay.stockeme.model.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.jay.stockeme.Constant
import com.jay.stockeme.model.datamodel.User
import kotlinx.coroutines.tasks.await

class AuthenticationRepository {
    private val auth = FirebaseAuth.getInstance()

    // Sign Up
    // Sign Up
    suspend fun signUpWithEmailAndPassword(email: String, password: String): Result<String?> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = authResult.user?.uid
            if (uid != null) {
                Result.success(uid)
            } else {
                Result.failure(Exception(Constant.UNABLE_TO_GET_ID))
            }
        } catch (e: FirebaseAuthWeakPasswordException) {
            Result.failure(e) // Weak password
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(e) // Invalid email format
        } catch (e: FirebaseAuthUserCollisionException) {
            Result.failure(e) // User already exists
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Sign In
    suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: FirebaseAuthInvalidUserException) {
            Result.failure(e) // User not found
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(e) // Invalid credentials
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}