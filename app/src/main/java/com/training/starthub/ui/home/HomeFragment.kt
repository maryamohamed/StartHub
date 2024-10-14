package com.training.starthub.ui.home

import Product
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.R
import com.training.starthub.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var firestore: FirebaseFirestore
    private lateinit var newestAdapter: NewestAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firestore = FirebaseFirestore.getInstance()

        // Initialize the RecyclerView and Adapter
        newestAdapter = NewestAdapter(mutableListOf()) { position ->
            navigateToProductDetails(position)
        }
        binding.recyclerViewVewest.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newestAdapter
        }

        // Fetch the product list from Firestore
        fetchProductsFromFirebase()
    }

    private fun fetchProductsFromFirebase() {
        firestore.collection("Companies")
            .document("All-products")
            .collection("products") 
            .get()
            .addOnSuccessListener { querySnapshot ->
                val productList = querySnapshot.documents.mapNotNull { document ->
                    try {
                        // Extract product fields safely
                        val name = document.getString("name") ?: "N/A"
                        val price = document.getDouble("price") ?: 0.0
                        val description = document.getString("description") ?: "No description"
                        val category = document.getString("category") ?: "Unknown"
                        val company = document.getString("company") ?: "Unknown"
                        val image = document.getString("image") ?: ""

                        // Create and return the Product object
                        Product(name, price, description, category, company, image)
                    } catch (e: Exception) {
                        Log.e("HomeFragment", "Error parsing product: ${e.message}")
                        null  // Skip invalid documents
                    }
                }
                newestAdapter.setData(productList)  // Update the RecyclerView adapter
            }
            .addOnFailureListener { exception ->
                Log.e("HomeFragment", "Error fetching products", exception)
            }
    }

    private fun navigateToProductDetails(position: Int) {
        findNavController().navigate(R.id.action_navigation_home_to_productDetailsFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
