package com.training.starthub.ui.signup.customer

import android.content.Context
import android.view.View
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.training.starthub.R
import com.training.starthub.utils.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CustomerRepo {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        phone: String,
    ): FirebaseUser? {
        return try {
            val user = withContext(Dispatchers.IO) {
                auth.createUserWithEmailAndPassword(email, password).await()
                auth.currentUser
            }
            user?.let {
                saveUserToFirestore(it.uid, name, email, phone, "Customer")
                sendEmailVerification(it)
            }
            user
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun saveUserToFirestore(
        userId: String,
        name: String,
        email: String,
        phone: String,
        userType: String
    ) {
        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "phone" to phone,
            "userType" to userType
        )
        withContext(Dispatchers.IO) {
            db.collection("Customers").document(userId).set(user).await()
        }

        withContext(Dispatchers.IO) {
            db.collection("Customers/${auth.currentUser!!.uid}/Profile")
                .document(auth.currentUser!!.uid)
                .set(user, SetOptions.merge()).await()
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