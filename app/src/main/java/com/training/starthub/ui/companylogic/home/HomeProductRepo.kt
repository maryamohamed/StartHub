package com.training.starthub.ui.companylogic.home

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.ui.model.Product
import kotlinx.coroutines.tasks.await

class HomeProductRepo {

    private val db = FirebaseFirestore.getInstance()

    suspend fun getUserProducts( userId: String = FirebaseAuth.getInstance().currentUser?.uid.toString()): List<Product> {
        val productList = mutableListOf<Product>()

        if (userId != null) {
            val productsCollection = db.collection("Companies")
                .document(userId)
                .collection("Products")

            val result = productsCollection.get().await()
            for (document in result) {
                val product = document.toObject(Product::class.java)
                productList.add(product)
            }
        }

        return productList
    }
}