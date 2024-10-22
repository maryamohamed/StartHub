package com.training.starthub.ui.customerlogic.favorites

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.ui.model.CustomerProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CustomerFavoriteRepo {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Function to fetch a list of products by a list of product IDs
    suspend fun getProductsByIds(productIds: List<String>): List<CustomerProduct> {
        val productList = mutableListOf<CustomerProduct>()

        withContext(Dispatchers.IO) {
            try {
                for (id in productIds) {
                    val documentSnapshot = db.collection("AllProducts")
                        .document(id)
                        .get()
                        .await()

                    if (documentSnapshot.exists()) {
                        val product = documentSnapshot.toObject(CustomerProduct::class.java)
                        product?.let { productList.add(it) }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return productList
    }

    // Function to fetch the favorite product IDs for the current user
    suspend fun getFavoritesIds(): HashMap<String, String> {
        val listOfIds = hashMapOf<String, String>()
        withContext(Dispatchers.IO) {
            val querySnapshot = db.collection("Customers")
                .document(FirebaseAuth.getInstance().currentUser?.uid ?: "")
                .collection("Favorites")
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
}
