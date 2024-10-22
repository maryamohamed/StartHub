package com.training.starthub.ui.customerlogic.allproduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.training.starthub.R
import com.training.starthub.databinding.FragmentAllProductsBinding
import com.training.starthub.ui.adapter.AllProductsAdapter
import com.training.starthub.ui.customerlogic.home.CustomerHomeViewModel


class AllProductsFragment : Fragment() {

    private var _binding: FragmentAllProductsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CustomerHomeViewModel by viewModels()
    private lateinit var allProductsAdapter: AllProductsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllProductsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleBackArrowClick()

        viewModel.loadProducts()
        allProductsAdapter = AllProductsAdapter(mutableListOf() ) { position ->
            navigateToProductDetails(position)
        }

        binding.recyclerAllProducts.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = allProductsAdapter
        }
        viewModel.products.observe(viewLifecycleOwner) { productList ->
            allProductsAdapter.setData(productList)
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        binding.recyclerAllProducts.setOnClickListener {
            navigateToProductDetails(it.tag as Int)
        }

    }

    private fun navigateToProductDetails(position: Int) {
        // Navigate to ProductDetailsFragment with the position as an argument
        val action = AllProductsFragmentDirections.actionNavProductsToProductDetailsFragment(position.toString())
        findNavController().navigate(action)
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