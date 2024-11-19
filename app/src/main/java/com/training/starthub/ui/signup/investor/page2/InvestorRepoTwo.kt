package com.training.starthub.ui.signup.investor.page2


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class InvestorRepoTwo {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()


    suspend fun saveInvestorData(userId: String, from: String, to: String, category: String): Result<String> {
        val investorData = hashMapOf(
            "from" to from,
            "to" to to,
            "category" to category
        )

        return try {
            withContext(Dispatchers.IO) {
                db.collection("Investors/$userId/secPage").document(userId).set(investorData).await()
            }

            withContext(Dispatchers.IO) {
                db.collection("Investors/$userId/Profile").document(userId).set(investorData, SetOptions.merge()).await()
            }

            Result.success("Investor data successfully added")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }
}