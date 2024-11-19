package com.training.starthub.ui.investorlogic.home.details

import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.ui.model.Company
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CompanyDetailsItemRepo {
    private val db = FirebaseFirestore.getInstance()

//    suspend fun fetchCompanyDetails(companyId: String): Map<String, String>? {
//        val docRef = db.collection("Companies").document(companyId)
//        val snapshot = docRef.get().await()
//        return if (snapshot.exists()) {
//            val name = snapshot.getString("name") ?: ""
//            val description = snapshot.getString("description") ?: ""
//            val category = snapshot.getString("category") ?: ""
//            val dateOfCreation = snapshot.getString("dateOfCreation") ?: ""
//            mapOf(
//                "name" to name,
//                "description" to description,
//                "category" to category,
//                "dateOfCreation" to dateOfCreation
//            )
//            } else {
//            null
//        }
//    }


    suspend fun getCompaniesIds(): HashMap<String, String> {
        val listOfIds = hashMapOf<String, String>()
        withContext(Dispatchers.IO) {
            val querySnapshot = db.collection("Companies")
                .get()
                .await()
            var i = 0
            for (document in querySnapshot) {
                listOfIds["$i"] = document.id
                i++
            }
        }
        return listOfIds
    }


    suspend fun fetchCompanyDetails(productId: String): Company? {
        return withContext(Dispatchers.IO) {
            val productRef = db.collection("Companies").document(productId).collection("secPage").document(productId)
            val documentSnapshot = productRef.get().await()
            if (documentSnapshot.exists()) {
                documentSnapshot.toObject(Company::class.java)
            } else {
                null
            }
        }
    }

    suspend fun fetchCompanyInfo(productId: String): Company? {
        return withContext(Dispatchers.IO) {
            val productRef = db.collection("Companies").document(productId)
            val documentSnapshot = productRef.get().await()
            if (documentSnapshot.exists()) {
                documentSnapshot.toObject(Company::class.java)
            } else {
                null
            }
        }
    }
}