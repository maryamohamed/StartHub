package com.training.starthub.ui.signup.company.page2

import android.content.Context
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.utils.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CompanyRepoTwo constructor(val view: View, private val context: Context, private val db: FirebaseFirestore, private val auth: FirebaseAuth) {

    suspend fun saveUserToFirestore( dateOfCreation: String, description: String, category: String){

        val companyData = hashMapOf(
            "dateOfCreation" to dateOfCreation,
            "description" to description,
            "category" to category
        )

        withContext(Dispatchers.IO) {

            try {
                db.collection("Companies/${auth.currentUser!!.uid}/secPage").document(auth.currentUser!!.uid)
                    .set(companyData).addOnSuccessListener {
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
                ToastUtil.showToast(context = context, "${e.message}")
            }

        }

    }


}