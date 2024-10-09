package com.training.starthub.ui.login.company

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class LoginCompanyRepo {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun signInUser(email: String, password: String): Result<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}