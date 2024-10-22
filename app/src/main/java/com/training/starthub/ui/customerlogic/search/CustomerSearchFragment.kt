package com.training.starthub.ui.customerlogic.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.training.starthub.databinding.FragmentCustomerSearchBinding
import com.training.starthub.ui.adapter.SearchAdapter
import com.training.starthub.ui.customerlogic.home.CustomerHomeViewModel

class CustomerSearchFragment : Fragment() {

    private var _binding: FragmentCustomerSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchAdapter: SearchAdapter

    private val homeViewModel: CustomerHomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerSearchBinding.inflate(inflater, container, false)
        binding.emptyStateTextView.text = "Start typing to search for products."
        binding.emptyStateTextView.visibility = View.VISIBLE
        binding.searchCardView.setOnClickListener { binding.recyclerSearch.visibility = View.VISIBLE
        setupRecyclerView()
        setupSearchListener()
        observeProductsData()
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        searchAdapter = SearchAdapter()
        binding.recyclerSearch.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = searchAdapter
        }
        searchAdapter.updateData(emptyList())
    }

    private fun setupSearchListener() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchAdapter.filter(s.toString())
                handleEmptyState()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun observeProductsData() {
        homeViewModel.products.observe(viewLifecycleOwner) { products ->
            if (products != null) {
                searchAdapter.updateData(products)
                handleEmptyState()
            }
        }
        homeViewModel.loadProducts()
    }

    private fun handleEmptyState() {
        val query = binding.searchEditText.text.toString()
        if (searchAdapter.itemCount == 0 && query.isEmpty()) {
            binding.recyclerSearch.visibility = View.GONE
            binding.emptyStateTextView.text = "Start typing to search for products."
            binding.emptyStateTextView.visibility = View.VISIBLE
        } else if (searchAdapter.itemCount == 0 && query.isNotEmpty()) {
            binding.recyclerSearch.visibility = View.GONE
            binding.emptyStateTextView.text = "No offers found for your search."
            binding.emptyStateTextView.visibility = View.VISIBLE
        } else {
            binding.recyclerSearch.visibility = View.VISIBLE
            binding.emptyStateTextView.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
