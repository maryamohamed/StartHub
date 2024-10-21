package com.training.starthub.ui.customerlogic.rate

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RateProductRepo {
    private val db = FirebaseFirestore.getInstance()


    suspend fun setProductRating(productId: String, rating: Double, feedback: String, imageUrl: String, name: String) {
        val ratingMap = hashMapOf(
            "rating" to rating,
            "feedback" to feedback,
            "imageUrl" to imageUrl,
            "name" to name

        )

        withContext(Dispatchers.IO) {
            db.collection("AllProducts")
                .document(productId)
                .collection("Ratings")
                .document(productId)
                .set(ratingMap, SetOptions.merge())
                .addOnSuccessListener {
                    Log.d("RateProductRepo", "Rating set successfully")
                }
                .addOnFailureListener { e ->
                    Log.e("RateProductRepo", "Error setting rating", e)
                }
                .await()
        }
    }


    suspend fun getIds(): HashMap<String, String> {
        val listOfIds = hashMapOf<String, String>()
        withContext(Dispatchers.IO) {
            val querySnapshot = db.collection("AllProducts")
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

    suspend fun fetchUserData(userId: String): Map<String, String>? {
        return withContext(Dispatchers.IO) {
            try {
                val docRef = db.collection("Customers").document(userId).collection("Profile")
                    .document(userId)
                val snapshot = docRef.get().await()
                if (snapshot.exists()) {
                    val email = snapshot.getString("email") ?: ""
                    val phone = snapshot.getString("phone") ?: ""
                    val imageUrl = snapshot.getString("imageUrl") ?: ""
                    val name = snapshot.getString("name") ?: ""

                    mapOf(
                        "email" to email,
                        "phone" to phone,
                        "imageUrl" to imageUrl,
                        "name" to name
                    )
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }
}