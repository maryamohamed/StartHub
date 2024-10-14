package com.training.starthub.ui.company.add

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.*

class ProductRepository {

    private val storage = FirebaseStorage.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // دالة لرفع الصورة باستخدام Coroutines
    suspend fun uploadImage(fileUri: Uri): String {
        val storageRef = storage.reference
        val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg")
        imageRef.putFile(fileUri).await()  // بنستخدم await عشان نخلي الـ coroutine يستنى
        return imageRef.downloadUrl.await().toString()  // بنرجع رابط الصورة بعد الرفع
    }

    // دالة لحفظ بيانات المنتج باستخدام Coroutines
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
            .await()  // بنستخدم await عشان نخلي العملية تنتظر لحد ما يتم إضافة المنتج
    }
}
