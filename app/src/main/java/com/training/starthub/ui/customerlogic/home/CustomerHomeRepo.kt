package com.training.starthub.ui.customerlogic.home

import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.ui.model.CustomerProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CustomerHomeRepo {


    private val firestore = FirebaseFirestore.getInstance()

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

}
