package com.training.starthub.ui.companylogic.addproduct

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
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
        var productDetails = hashMapOf<String, Any>()

        val docRef = db.collection("Companies")
            .document(auth.currentUser!!.uid).collection("secPage")
            .document(auth.currentUser!!.uid)

        // Get the data from the document
        val data = docRef.get().await()


        val companyName = data.getString("name") ?: ""

//        val snapshot = docRef.get().await()
        if (data.exists()) {
            val companyLogo = data.getString("imageUrl") ?: ""

            productDetails = hashMapOf(
                "name" to name,
                "description" to description,
                "price" to price.toInt(),
                "category" to category,
                "imageUrl" to imageUrl,
                "CompanyLogo" to companyLogo,
                "CompanyName" to companyName
            )
        }
        else{

            productDetails = hashMapOf(
                "name" to name,
                "description" to description,
                "price" to price.toInt(),
                "category" to category,
                "imageUrl" to imageUrl,
                "CompanyName" to companyName

            )
        }

        val userId = auth.currentUser?.uid ?: throw IllegalStateException("User not logged in")
        db.collection("Companies")
            .document(userId)
            .collection("Products").document()
            .set(productDetails, SetOptions.merge())
            .await()

        db.collection("AllProducts").document().set(productDetails, SetOptions.merge()).await()
    }
}