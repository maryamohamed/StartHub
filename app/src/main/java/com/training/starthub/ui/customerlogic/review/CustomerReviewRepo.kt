package com.training.starthub.ui.customerlogic.review

import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.ui.model.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CustomerReviewRepo {
    private val db = FirebaseFirestore.getInstance()

    suspend fun fetchReviews(productId: String): List<Review> {
        val productList = mutableListOf<Review>()
        return try {
            withContext(Dispatchers.IO) {
                val querySnapshot = db.collection("AllProducts")
                    .document(productId)
                    .collection("Ratings")
                    .get()
                    .await()

                for (document in querySnapshot) {
                    val product = document.toObject(Review::class.java)
                    productList.add(product)
                }
            }
            productList
        } catch (e: Exception) {
            throw Exception("Failed to fetch reviews: ${e.message}")
        }
    }

    suspend fun getIds(): HashMap<String, String> {
        val listOfIds = hashMapOf<String, String>()
        return try {
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
            listOfIds
        } catch (e: Exception) {
            throw Exception("Failed to fetch product IDs: ${e.message}")
        }
    }
}
