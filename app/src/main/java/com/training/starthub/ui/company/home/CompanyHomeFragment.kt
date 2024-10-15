package com.training.starthub.ui.company.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.starthub.R
import com.training.starthub.databinding.FragmentCompanyHomeBinding
import com.training.starthub.ui.adapter.ProductsAdapter
import com.training.starthub.ui.model.Product

class CompanyHomeFragment : Fragment() {

    private lateinit var binding: FragmentCompanyHomeBinding
    private val productViewModel: HomeProductViewModel by viewModels()

    // Declare the adapter once
    private lateinit var adapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the adapter with an empty list
        adapter = ProductsAdapter(mutableListOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Load products and observe changes
        productViewModel.loadUserProducts()
        productViewModel.products.observe(viewLifecycleOwner) { productList ->
            // Clear existing data and add new products
            adapter.clearProducts()
            adapter.addAll(productList)
            adapter.notifyDataSetChanged()  // Notify adapter to refresh the UI
        }

        productViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        // Set up navigation to other fragments
        binding.addProduct.setOnClickListener {
            findNavController().navigate(R.id.action_CompanyHomeFragment_to_CompanyAddProductFragment)
        }

        binding.profile.setOnClickListener {
            findNavController().navigate(R.id.action_CompanyHomeFragment_to_CompanyProfileFragment)
        }
    }
}
