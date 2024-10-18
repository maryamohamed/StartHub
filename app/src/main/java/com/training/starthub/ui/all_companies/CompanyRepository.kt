package com.training.starthub.ui.all_companies

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.ui.model.Company
import kotlinx.coroutines.tasks.await

class CompanyRepository {

    private val db = FirebaseFirestore.getInstance()

    suspend fun fetchCompanies(): List<Company> {
        val companiesList = mutableListOf<Company>()

        try {
            val result = db.collection("Companies").get().await()
            for (document in result) {
                val companyId = document.id
                val companyName = document.getString("name") ?: "No Name"
                Log.d("FirestoreData", "Processing company: $companyName")

                val subCollectionResult = db.collection("Companies")
                    .document(companyId)
                    .collection("secPage")
                    .get()
                    .await()

                for (subDoc in subCollectionResult) {
                    val description = subDoc.getString("description") ?: "No Description"
                    val imageUrl = subDoc.getString("imageUrl") ?: "No Image"
                    val companyCategory = subDoc.getString("category") ?: "No Category"

                    val company = Company(
                        name = companyName,
                        category = companyCategory,
                        description = description,
                        imageUrl = imageUrl
                    )
                    companiesList.add(company)
                }
            }
        } catch (e: Exception) {
            Log.e("FirestoreError", "Error fetching companies: ", e)
        }

        return companiesList
    }
}
