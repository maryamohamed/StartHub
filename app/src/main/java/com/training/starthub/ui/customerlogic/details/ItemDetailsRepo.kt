package com.training.starthub.ui.customerlogic.details

import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.ui.model.CustomerProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ItemDetailsRepo {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun fetchProductDetails(productId: String): CustomerProduct? {
        return withContext(Dispatchers.IO) {
            val productRef = db.collection("AllProducts").document(productId)
            val documentSnapshot = productRef.get().await()
            if (documentSnapshot.exists()) {
                documentSnapshot.toObject(CustomerProduct::class.java)
            } else {
                null
            }
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
}
