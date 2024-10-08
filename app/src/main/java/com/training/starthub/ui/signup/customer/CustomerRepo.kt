package com.training.starthub.ui.signup.customer

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.utils.ToastUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CustomerRepo constructor( private val context: Context, private val db: FirebaseFirestore, private val auth: FirebaseAuth) {

    // user: User
    suspend fun saveUserToFirestore(name: String, email: String, phone: String, password: String){
        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "phone" to phone,
            "password" to password)

        withContext(Dispatchers.IO) {


            // Check if the user is already authenticated
//            if (auth.currentUser == null) {
                try {
                    auth.createUserWithEmailAndPassword(email, password).await()
                    sendEmailVerification(auth.currentUser!!)
                    db.collection("customers").document(auth.currentUser!!.uid).set(user)
                        .addOnSuccessListener {
                            ToastUtil.showToast(
                                context = context,
                                "User data successfully added to Firestore."
                            )
                        }
                        .addOnFailureListener { e ->
                            ToastUtil.showToast(
                                context = context,
                                "Error adding user data to Firestore: ${e.message}"
                            )
                        }.await()
                }catch (e: Exception){
                    ToastUtil.showToast(context = context,"Failed to create account: ${e.message}")
                }
//            }
//            else{
//                ToastUtil.showToast(context = context,"User is already authenticated.")
//            }



        }

    }


    private suspend fun sendEmailVerification(user: FirebaseUser){
        try {
        user.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    ToastUtil.showToast(context = context,"Verification email sent to ${user.email}")
                } else {
                    ToastUtil.showToast(context = context,"Error sending verification email: ${task.exception?.message}")
                }
            }.await()
        }catch (e: Exception){
            ToastUtil.showToast(context = context,"Failed to create account: ${e.message}")
        }

    }


}