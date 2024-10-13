package com.training.starthub.ui.signup.company.page2

import android.content.Context
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.training.starthub.utils.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CompanyRepoTwo constructor(val view: View, private val context: Context, private val db: FirebaseFirestore, private val auth: FirebaseAuth) {

    suspend fun saveUserToFirestore( dateOfCreation: String, description: String, category: String ){

        val companyData = hashMapOf(
            "dateOfCreation" to dateOfCreation,
            "description" to description,
            "category" to category,
//            "name" to name
        )

        withContext(Dispatchers.IO) {

            try {
                db.collection("Companies/${auth.currentUser!!.uid}/secPage").document(auth.currentUser!!.uid)
                    .set(companyData, SetOptions.merge()).addOnSuccessListener {
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

//    suspend fun saveNameToFirestore(): Map<String, String>?{
//
//        val docRef = db.collection("Companies").document(auth.currentUser!!.uid)
//        val snapshot = docRef.get().await()
//        val name = snapshot.getString("name") ?: ""
//
//        return if (snapshot.exists()) {
//            mapOf("name" to name)
//        } else {
//            null
//        }
//    }


}