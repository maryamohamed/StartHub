package com.training.starthub.ui.customerlogic.home

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.ui.model.CustomerProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CustomerHomeRepo {


    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun fetchProducts(): List<CustomerProduct> {
        val productList = mutableListOf<CustomerProduct>()
        withContext(Dispatchers.IO) {

            val querySnapshot = firestore.collection("AllProducts")
                .get()
                .await()
            for (document in querySnapshot) {
                val product = document.toObject(CustomerProduct::class.java)
                productList.add(product)
            }
        }
        return productList
    }

    suspend fun fetchUserName(userId: String): Map<String, String>? {
        return withContext(Dispatchers.IO) {
            try {
                val docRef = firestore.collection("Customers").document(userId).collection("Profile")
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
