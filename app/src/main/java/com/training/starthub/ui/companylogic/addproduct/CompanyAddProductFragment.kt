package com.training.starthub.ui.companylogic.addproduct

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.training.starthub.R
import com.training.starthub.databinding.FragmentCompanyAddProductBinding
import com.training.starthub.utils.ToastUtil

class CompanyAddProductFragment : Fragment() {

    private lateinit var binding: FragmentCompanyAddProductBinding
    private var REQUEST_IMAGE_PICK = 1
    private lateinit var selectedImageUri: Uri
    private val productViewModel: ProductAddViewModel by viewModels()
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
                    productViewModel.uploadProduct(selectedImageUri, name, description, price, category)
                } else {
                    ToastUtil.showToast(requireContext(), "Please select an image first")
                }
            }
        }

        // متابعة الرسائل اللي جاية من الـ ViewModel
        productViewModel.successMessage.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_CompanyAddProductFragment_to_CompanyHomeFragment)
        })

        productViewModel.errorMessage.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })
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