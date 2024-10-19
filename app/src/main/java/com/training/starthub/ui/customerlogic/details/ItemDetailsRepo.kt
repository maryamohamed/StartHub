package com.training.starthub.ui.customerlogic.details

import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.ui.model.CustomerProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ItemDetailsRepo {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()


    suspend fun fetchProductDetails(productId: String) : CustomerProduct {
        var product: CustomerProduct? = null
        withContext(Dispatchers.IO) {
            val productRef = db.collection("All_Products").document(productId)
            productRef.get().addOnSuccessListener { documentSnapshot ->
                product = documentSnapshot.toObject(CustomerProduct::class.java)
                return@addOnSuccessListener
            }.addOnFailureListener {
                return@addOnFailureListener
            }
        }
        return product!!

    }


    suspend fun getIds(): HashMap<Int, String> {
        val listOfIds = hashMapOf<Int, String>()
        withContext(Dispatchers.IO) {
            val querySnapshot = db.collection("AllProducts")
                .get()
                .await()
            var i = 0
            for (document in querySnapshot) {
                listOfIds.put(i, document.id)
                i++
            }
        }
        return listOfIds
    }
}