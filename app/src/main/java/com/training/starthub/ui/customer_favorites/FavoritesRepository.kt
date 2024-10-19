package com.training.starthub.ui.customer_favorites

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.ui.model.Product

class FavoritesRepository {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Fetch favorite products
    fun fetchFavoriteProducts(productListLiveData: MutableLiveData<List<Product>>) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        db.collection("customers")
            .document(userId)
            .collection("Favorites")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val productList = mutableListOf<Product>()
                if (!querySnapshot.isEmpty) {
                    for (document in querySnapshot.documents) {
                        val product = document.toObject(Product::class.java)
                        product?.let { productList.add(it) }
                    }
                }
                productListLiveData.value = productList
            }
            .addOnFailureListener {
                productListLiveData.value = null  // Handle failure
            }
    }
}
