package com.training.starthub.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.training.starthub.databinding.FragmentCompanyAddProductBinding
import java.util.UUID
import android.provider.Settings
import android.widget.ArrayAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.ui.adapter.Product
import com.training.starthub.utils.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class CompanyAddProductFragment : Fragment() {

    private lateinit var binding: FragmentCompanyAddProductBinding
//    private val REQUEST_IMAGE_PERMISSION = 200
    private var REQUEST_IMAGE_PICK = 1

    // Declare selectedImageUri as a class-level variable
    private lateinit var selectedImageUri: Uri

    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference
    private lateinit var db: FirebaseFirestore
    private  lateinit var auth: FirebaseAuth
    private val list: List<String> = listOf("Choose Category", "Tech", "Clothes", "Food", "Sports", "Other")


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode,
            data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {

            selectedImageUri = data.data!!

            selectedImageUri?.let {
                // Set the image to the view (optional)
                binding.uploadImage.setImageURI(it)

            }


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCompanyAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.category.adapter = adapter






        binding.uploadImage.setOnClickListener {
            openGallery()  // Open the gallery to pick an image
        }


        binding.post.setOnClickListener {
            val description = binding.description.text.toString().trim()
            val price = binding.price.text.toString().trim()
            val category = binding.category.selectedItem.toString().trim()
            val name = binding.productName.text.toString().trim()

            // Pass the selected image URI to the uploadImage function
            selectedImageUri?.let {
                // Upload the image to Firebase Storage
                uploadImage(it)
            }
            if (isValidData(name,description, price, category)) {
                saveProductData(getCurrentUserId()!!,name ,description, price, category, selectedImageUri)
            }
        }



    }


    // Open the gallery to pick an image
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_PICK)

    }




    private fun uploadImage(fileUri: Uri) {
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference

        val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg") // Assuming storage & storageRef are initialized

        val uploadTask = imageRef.putFile(fileUri)
        uploadTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(requireContext(), "Image uploaded successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(),
                "Image upload failed", Toast.LENGTH_SHORT).show()
            }
        }
    }


     private fun saveProductData(userId: String, name: String, description: String, price: String, category: String, selectedImageUri: Uri): Result<String> {
        val productDetails = hashMapOf(
            "name" to name,
            "Description" to description,
            "price" to price,
            "category" to category,
            "image" to selectedImageUri
        )

        return try {
//            withContext(Dispatchers.IO) {
                db.collection("Companies/${getCurrentUserId()}/secPage/${getCurrentUserId()}/Posts/${getCurrentUserId()}/Products").document(userId).set(productDetails)
//            }
            Result.success("Investor data successfully added")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    private fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }


    private fun isValidData(name: String, description: String, price: String, category: String): Boolean {
        return when {
            name.isEmpty() -> {
                ToastUtil.showToast(requireContext(),"Name is required")
                false
            }
            price.isEmpty() -> {
                ToastUtil.showToast(requireContext(),"Price is required")
                false
            }
            description.isEmpty() -> {
                ToastUtil.showToast(requireContext(),"Description is required")
                false
            }
            category.isEmpty() || category == "Choose Category" -> {
                ToastUtil.showToast(requireContext(),"Category is required")
                false
            }
            else -> true
        }
    }


}
