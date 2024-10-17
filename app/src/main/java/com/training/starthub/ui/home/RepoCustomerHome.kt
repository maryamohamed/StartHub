package com.training.starthub.ui.home

import Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RepoCustomerHome {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun fetchProducts(): List<Product> {
        val productList = mutableListOf<Product>()

        val querySnapshot = firestore.collection("Companies")
            .document("All-products")
            .collection("products")
            .get()
            .await()

        for (document in querySnapshot.documents) {
            val product = document.toObject(Product::class.java)
            product?.let { productList.add(it) }
        }

        return productList
    }
}