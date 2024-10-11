package com.training.starthub.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.training.starthub.R
import com.training.starthub.databinding.FragmentAllProductsBinding

class AllProductsFragment : Fragment() {

    private var _binding: FragmentAllProductsBinding? = null
    private val binding get() = _binding!!

    private lateinit var allProductsAdapter: AllProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllProductsBinding.inflate(inflater, container, false)

        setupRecyclerView()
        handleBackArrowClick()

        return binding.root
    }

    private fun setupRecyclerView() {
        allProductsAdapter = AllProductsAdapter()
        binding.recyclerAllProducts.adapter = allProductsAdapter

    }

    private fun handleBackArrowClick() {
        binding.backArrow.setOnClickListener {
            findNavController().navigate(R.id.action_nav_products_to_navigation_home)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
