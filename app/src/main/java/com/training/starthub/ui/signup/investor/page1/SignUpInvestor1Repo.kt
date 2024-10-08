package com.training.starthub.ui.signup.investor.page1

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SignupInvestorRepo {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun registerUser(name: String, email: String, password: String, phone: String): FirebaseUser? {
        return try {
            val user = withContext(Dispatchers.IO) {
                auth.createUserWithEmailAndPassword(email, password).await()
                auth.currentUser
            }
            user?.let {
                saveUserToFirestore(it.uid, name, email, phone)
                sendEmailVerification(it)
            }
            user
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun saveUserToFirestore(userId: String, name: String, email: String, phone: String) {
        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "phone" to phone
        )
        withContext(Dispatchers.IO) {
            db.collection("Investors").document(userId).set(user).await()
        }
    }

    private suspend fun sendEmailVerification(user: FirebaseUser) {
        withContext(Dispatchers.IO) {
            user.sendEmailVerification().await()
        }
    }

    suspend fun checkEmailVerification(
        user: FirebaseUser,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        while (true) {
            user.reload()
            if (user.isEmailVerified) {
                withContext(Dispatchers.Main) { onSuccess() }
                break
            } else {
                withContext(Dispatchers.Main) { onFailure() }
                delay(5000)
            }
        }
    }
}
