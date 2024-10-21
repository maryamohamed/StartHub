package com.training.starthub.ui.signup.company.page1

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

class CompanyRepoOne constructor(val view: View, private val context: Context, private val db: FirebaseFirestore, private val auth: FirebaseAuth) {

    // user: User
    suspend fun saveUserToFirestore(name: String, email: String, phone: String, password: String, userType: String){
        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "phone" to phone,
            "password" to password,
            "userType" to userType)
        val name = hashMapOf(
            "name" to name)
        val companyName = hashMapOf(
            "name" to name)

        withContext(Dispatchers.IO) {

            // Check if the user is already authenticated
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                sendEmailVerification(auth.currentUser!!)
                checkEmailVerification(auth.currentUser!!)

                db.collection("Companies").document(auth.currentUser!!.uid).set(user)
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


                checkEmailVerification(auth.currentUser!!)
            }catch (e: Exception){
                ToastUtil.showToast(context = context,"Failed to create account: ${e.message}")
            }

        }

        withContext(Dispatchers.IO){
            db.collection("Companies/${auth.currentUser!!.uid}/secPage").document(auth.currentUser!!.uid)
                .set(name).addOnSuccessListener {
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

        }

        withContext(Dispatchers.IO){
            db.collection("Companies/${auth.currentUser!!.uid}/Products").document(auth.currentUser!!.uid)
                .set(companyName , SetOptions.merge())
                .addOnFailureListener { e ->
                    ToastUtil.showToast(
                        context = context,
                        "Error adding Company name to Firestore: ${e.message}"
                    )
                }.await()

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
                    view.findNavController().navigate(R.id.action_SignupCompanyFragment_to_SecPageCompanyFragment)
                }
                break

            } else {
                delay(1000)
            }
        }

    }




}