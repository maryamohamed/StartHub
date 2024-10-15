package com.training.starthub.ui.signup.customer

import android.content.Context
import android.view.View
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.R
import com.training.starthub.utils.ToastUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CustomerRepo constructor(val view: View, private val context: Context, private val db: FirebaseFirestore, private val auth: FirebaseAuth) {

    // user: User
    suspend fun saveUserToFirestore(name: String, email: String, phone: String, password: String){
        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "phone" to phone,
            "password" to password)

        val info = hashMapOf(
            "name" to name,
            "email" to email,
            "phone" to phone)

        withContext(Dispatchers.IO) {

            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                sendEmailVerification(auth.currentUser!!)
                checkEmailVerification(auth.currentUser!!)

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

                db.collection("customers").document(auth.currentUser!!.uid)
                    .collection("Profile")
                    .document(auth.currentUser!!.uid).set(info).addOnFailureListener{
                        ToastUtil.showToast(context = context,"Failed to save Personal Information: ${it.message}")
                    }.await()

            }catch (e: Exception){
                ToastUtil.showToast(context = context,"Failed to create account: ${e.message}")
            }




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

    suspend fun checkEmailVerification(user: FirebaseUser = auth.currentUser!!){


        while (true) {

            user.reload()
            if (user.isEmailVerified) {
                ToastUtil.showToast(context = context, "Email verified")
                withContext(Dispatchers.Main){
                    view.findNavController().navigate(R.id.action_SignupCustomerFragment_to_CustomerProfileFragment)
                }
                break

            } else {
                delay(1000)
            }
        }
    }


}