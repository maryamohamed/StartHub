package com.training.starthub.ui.company.add

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.*

class ProductAddRepo {

    private val storage = FirebaseStorage.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()


    suspend fun uploadImage(fileUri: Uri): String {
        val storageRef = storage.reference
        val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg")
        imageRef.putFile(fileUri).await()
        return imageRef.downloadUrl.await().toString()
    }

    suspend fun saveProductData(name: String, description: String, price: String, category: String, imageUrl: String) {
        val productDetails = hashMapOf(
            "name" to name,
            "description" to description,
            "price" to price.toInt(),
            "category" to category,
            "imageUrl" to imageUrl
        )

        val userId = auth.currentUser?.uid ?: throw IllegalStateException("User not logged in")
        db.collection("Companies")
            .document(userId)
            .collection("Products")
            .add(productDetails)
            .await()
    }
}