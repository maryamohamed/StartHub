package com.training.starthub.ui

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.training.starthub.utils.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID


/*
class CompanyProfileRepo(private val context: Context, private val auth: FirebaseAuth) {
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    private val db = FirebaseFirestore.getInstance()

    suspend fun uploadImage(fileUri: Uri): Uri? {
        return try {
//            ToastUtil.showToast(context, "uploadImage: $fileUri")

            val imageRef = storageRef.child("profile_images/${UUID.randomUUID()}.jpg")
            imageRef.putFile(fileUri).await()
            imageRef.downloadUrl.await()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun downloadImage(imageId: String): File? {
        return try {
//            ToastUtil.showToast(context, "downloadImage: $imageId")

            val storageReference = storageRef.child("profile_images/$imageId.jpg")
            val file = File.createTempFile("tempImage", ".jpg")
            storageReference.getFile(file).await()
            file
        } catch (e: Exception) {
            null
        }
    }

    suspend fun fetchUserDescriptionAndCategory(): Map<String, String>? {
        return withContext(Dispatchers.IO) {
            try {
//                ToastUtil.showToast(context, "fetchUserDescriptionAndCategory: $userId")

                val docRef = db.collection("Companies/${auth.currentUser!!.uid}/secPage").document(auth.currentUser!!.uid)

                val snap = docRef.get().await()
                if (snap.exists()) {

                    val description = snap.getString("description") ?: ""
                    val category = snap.getString("category") ?: ""
                    val dateOfCreation = snap.getString("DateOfCreation") ?: ""
                    val imageId = snap.getString("imageId") ?: ""
                    val docRef = db.collection("Companies/$auth.currentUser!!.uid}").document(auth.currentUser!!.uid)
                    val snapshot = docRef.get().await()
                    var name = ""
                    if (snapshot.exists()) {
                        name = snapshot.getString("name") ?: ""
                    }
                    mapOf("description" to description, "imageId" to imageId , "name" to name , "category" to category , "dateOfCreation" to dateOfCreation)
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }

//    suspend fun fetchPersonalData(): Map<String, String>? {
//        return withContext(Dispatchers.IO) {
//            try {
////                ToastUtil.showToast(context, "fetchPersonalData: $userId")
//
//                    val docRef = db.collection("Companies/$auth.currentUser!!.uid}").document(auth.currentUser!!.uid)
//                val snapshot = docRef.get().await()
//                if (snapshot.exists()) {
//                    val name = snapshot.getString("name") ?: ""
//
//                    mapOf("name" to name)
//                } else {
//                    null
//                }
//            } catch (e: Exception) {
//                null
//            }
//        }
//    }

    suspend fun saveUpdatedToFirestore(imageId: String,oldData: Map<String,Any>) {
        val imageIdMap = hashMapOf(
            "imageId" to imageId,
            "description" to oldData["description"],
            "category" to oldData["category"],
            "DateOfCreation" to oldData["dateOfCreation"],
            "name" to oldData["name"],
        )
//        val personalData = hashMapOf(
//            "name" to personalData["name"],
//            "email" to personalData["email"],
//            "phone" to personalData["phone"],
//            "password" to personalData["password"]
//        )


//        withContext(Dispatchers.IO) {
//            db.collection("Companies/${auth.currentUser!!.uid}")
//                .document(auth.currentUser!!.uid).set(personalData as Map<String, Any>, SetOptions.merge()).await()
//        }

        withContext(Dispatchers.IO) {
            try {

                db.collection("Companies/${auth.currentUser!!.uid}/secPage")
                    .document(auth.currentUser!!.uid).update(imageIdMap as Map<String, Any> ).await()


            } catch (e: Exception) {
//                ToastUtil.showToast(context, e.message.toString())
            }


        }
    }
}*/


class CompanyProfileRepo(private val context: Context, private val auth: FirebaseAuth) {
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

    suspend fun downloadImage(imageId: String): File? {
        return try {
            val storageReference = storageRef.child("profile_images/$imageId.jpg")
            val file = File.createTempFile("tempImage", ".jpg")
            storageReference.getFile(file).await()
            file
        } catch (e: Exception) {
            null
        }
    }

    suspend fun fetchUserDescriptionAndCategory(userId: String): Map<String, String>? {
        return withContext(Dispatchers.IO) {
            try {
                val docRef = db.collection("Companies/$userId/secPage").document(userId)
                val snapshot = docRef.get().await()
                if (snapshot.exists()) {
                    val description = snapshot.getString("description") ?: ""
                    val category = snapshot.getString("category") ?: ""
                    val dateOfCreation = snapshot.getString("dateOfCreation") ?: ""
                    val name = snapshot.getString("name") ?: ""
                    if(snapshot.contains("imageId")){
                        val imageId = snapshot.getString("imageId") ?: ""
                        mapOf("description" to description, "category" to category , "dateOfCreation" to dateOfCreation , "imageId" to imageId , "name" to name )
                    }
                    else
                    mapOf("description" to description, "category" to category , "dateOfCreation" to dateOfCreation , "name" to name )
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }

//    suspend fun updateUserData(description: String, category: String) {
//        val updatedData = hashMapOf(
//            "description" to description,
//            "category" to category
//        )
//        withContext(Dispatchers.IO) {
//            try {
//                db.collection("Companies/${auth.currentUser!!.uid}/secPage")
//                    .document(auth.currentUser!!.uid)
//                    .update(updatedData as Map<String, Any>)
//                    .await()
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(context, "User data successfully updated in Firestore.", Toast.LENGTH_SHORT).show()
//                }
//            } catch (e: Exception) {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(context, "Error updating user data in Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }

    suspend fun saveImageIdToFirestore( oldData: Map<String, String> ) {
//        val imageIdMap = hashMapOf(userId to userId)
//        imageIdMap.putAll(oldData)
        val data = oldData.toMutableMap()
//        data["imageUrl"] = imageUrl
        withContext(Dispatchers.IO) {
            try {
                db.collection("Companies/${auth.currentUser!!.uid}/secPage")
                    .document(auth.currentUser!!.uid)
                    .set(data, SetOptions.merge()).await()  // Use merge to ensure it merges with existing data
            } catch (e: Exception) {
                // Handle exception if needed
            }
        }
    }
}