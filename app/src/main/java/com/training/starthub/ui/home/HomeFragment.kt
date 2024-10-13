package com.training.starthub.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.R
import com.training.starthub.data.local.Product
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
        super.onViewCreated(view,savedInstanceState)

        firestore = FirebaseFirestore.getInstance()

        // Initialize the adapter with an empty list
        newestAdapter = NewestAdapter(mutableListOf()) { position ->
            navigateToProductDetails(position)
        }
        binding.recyclerViewVewest.adapter = newestAdapter

        // Fetch products from Firestore
        fetchProductsFromFirebase()
    }

    private fun fetchProductsFromFirebase() {
        firestore.collection("Companies")
            .document("All-products")
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Extract the product fields directly from the document
                    val product = Product(
                        name = document.getString("name") ?: "N/A",
                        price = document.getDouble("price") ?: 0.0,
                        description = document.getString("description") ?: "No description",
                        category = document.getString("category") ?: "Unknown",
                        company = document.getString("company") ?: "Unknown",
                        image = document.getString("image") ?: ""
                    )
                    newestAdapter.setData(listOf(product)) // Update adapter with the product
                } else {
                    Log.d("HomeFragment", "No such document found!")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("HomeFragment", "Error fetching product", exception)
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
