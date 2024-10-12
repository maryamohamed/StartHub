package com.training.starthub.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.training.starthub.R
import com.training.starthub.databinding.FragmentCompanyHomeBinding
import com.training.starthub.ui.adapter.ProductsAdapter
import com.training.starthub.ui.model.Product

class CompanyHomeFragment : Fragment() {

    private lateinit var binding: FragmentCompanyHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompanyHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserProducts()
        binding.addProduct.setOnClickListener {
            findNavController().navigate(R.id.action_CompanyHomeFragment_to_CompanyAddProductFragment)
        }
        binding.profile.setOnClickListener {
            findNavController().navigate(R.id.action_CompanyHomeFragment_to_CompanyProfileFragment)
        }

    }

    private fun loadUserProducts() {
        val firestore = Firebase.firestore
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {
            val productsCollection = firestore.collection("Companies")
                .document(userId)
                .collection("Products")

            productsCollection.get()
                .addOnSuccessListener { result ->
                    val productList = mutableListOf<Product>()
                    for (document in result) {
                        val product = document.toObject(Product::class.java)
                        productList.add(product)
                    }
                    displayProducts(productList)
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Failed to fetch products", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun displayProducts(products: List<Product>) {
        val adapter = ProductsAdapter(products)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }
}
