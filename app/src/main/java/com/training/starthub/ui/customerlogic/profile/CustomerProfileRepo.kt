package com.training.starthub.ui.customerlogic.profile

import android.content.Context
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

class CustomerProfileRepo(private val context: Context, private val auth: FirebaseAuth) {
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    private val db = FirebaseFirestore.getInstance()

    suspend fun uploadImage(fileUri: Uri): Uri? {
        return try {
            val imageRef = storageRef.child("profile_images/${UUID.randomUUID()}.jpg")
            imageRef.putFile(fileUri).await()
            imageRef.downloadUrl.await()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun fetchUserName(userId: String): Map<String, String>? {
        return withContext(Dispatchers.IO) {
            try {
                val docRef = db.collection("Customers").document(userId).collection("Profile").document(userId)
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

    suspend fun updateUserImage(imageUrl: String?, oldData: Map<String, String>) {
        val data = oldData.toMutableMap()
        if (imageUrl != null) {
            data["imageUrl"] = imageUrl
        }

        withContext(Dispatchers.IO) {
            try {
                db.collection("Customers").document(auth.currentUser!!.uid).collection("Profile")
                    .document(auth.currentUser!!.uid)
                    .set(data, SetOptions.merge()).await()
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

    fun updateUserName(newName: String, map: Map<String, String>) {
        val data = map.toMutableMap()
        data["name"] = newName
        db.collection("Customers").document(auth.currentUser!!.uid).collection("Profile")
            .document(auth.currentUser!!.uid)
            .set(data, SetOptions.merge())
    }
}