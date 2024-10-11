package com.training.starthub.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.training.starthub.R
import com.training.starthub.databinding.FragmentCompanyAddProductBinding
import com.training.starthub.utils.ToastUtil
import java.util.UUID

class CompanyAddProductFragment : Fragment() {

    private lateinit var binding: FragmentCompanyAddProductBinding
    private var REQUEST_IMAGE_PICK = 1
    private lateinit var selectedImageUri: Uri
    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private val list: List<String> = listOf("Choose Category", "Tech", "Clothes", "Food", "Sports", "Other")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            openGallery()
        }

        binding.post.setOnClickListener {
            val name = binding.productName.text.toString().trim()
            val description = binding.description.text.toString().trim()
            val price = binding.price.text.toString().trim()
            val category = binding.category.selectedItem.toString().trim()

            if (isValidData(name, description, price, category)) {
                if (::selectedImageUri.isInitialized) {
                    uploadImage(selectedImageUri, name, description, price, category)
                } else {
                    ToastUtil.showToast(requireContext(), "Please select an image first")
                }
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data!!
            binding.uploadImage.setImageURI(selectedImageUri)
        }
    }

    private fun uploadImage(fileUri: Uri, name: String, description: String, price: String, category: String) {
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference
        val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg")

        val uploadTask = imageRef.putFile(fileUri)
        uploadTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    saveProductData(getCurrentUserId()!!, name, description, price, category, imageUrl)
                }
            } else {
                Toast.makeText(requireContext(), "Image upload failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveProductData(userId: String, name: String, description: String, price: String, category: String, imageUrl: String) {
        val productDetails = hashMapOf(
            "name" to name,
            "description" to description,
            "price" to price.toInt(),
            "category" to category,
            "imageUrl" to imageUrl
        )

        db.collection("Companies")
            .document(userId)
            .collection("Products")
            .add(productDetails)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Product added successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_CompanyAddProductFragment_to_CompanyHomeFragment)
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to add product", Toast.LENGTH_SHORT).show()
            }
    }

    private fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    private fun isValidData(name: String, description: String, price: String, category: String): Boolean {
        return when {
            name.isEmpty() -> {
                ToastUtil.showToast(requireContext(), "Name is required")
                false
            }
            price.isEmpty() -> {
                ToastUtil.showToast(requireContext(), "Price is required")
                false
            }
            description.isEmpty() -> {
                ToastUtil.showToast(requireContext(), "Description is required")
                false
            }
            category.isEmpty() || category == "Choose Category" -> {
                ToastUtil.showToast(requireContext(), "Category is required")
                false
            }
            else -> true
        }
    }
}
