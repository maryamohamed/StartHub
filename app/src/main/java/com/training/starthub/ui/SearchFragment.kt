package com.training.starthub.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.training.starthub.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchAdapter : SearchAdapter

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        setupRecyclerView()
        setupSearchListener()

        handleEmptyState()

        return binding.root
    }

    private fun setupRecyclerView() {
        searchAdapter = SearchAdapter()
        binding.recyclerSearch.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = searchAdapter
        }

        // Initially no data scenario
        searchAdapter.updateData(emptyList()) // No data yet
    }

    private fun setupSearchListener() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s : CharSequence?,
                start : Int,
                count : Int,
                after : Int
            ) {
            }

            override fun onTextChanged(s : CharSequence?, start : Int, before : Int, count : Int) {
                searchAdapter.filter(s.toString())
                handleEmptyState() // Check if RecyclerView should appear or not
            }

            override fun afterTextChanged(s : Editable?) {}
        })
    }

    private fun handleEmptyState() {
        val query = binding.searchEditText.text.toString()

        if (searchAdapter.itemCount == 0 && query.isEmpty()) {
            // No search yet, show placeholder and hide RecyclerView
            binding.recyclerSearch.visibility = View.GONE
            binding.emptyStateTextView.text = "Start typing to search for products."
            binding.emptyStateTextView.visibility = View.VISIBLE
        } else if (searchAdapter.itemCount == 0 && query.isNotEmpty()) {
            // No results found, show "no results" message
            binding.recyclerSearch.visibility = View.GONE
            binding.emptyStateTextView.text = "No offers found for your search."
            binding.emptyStateTextView.visibility = View.VISIBLE
        } else {
            // Results found, show RecyclerView and hide placeholder message
            binding.recyclerSearch.visibility = View.VISIBLE
            binding.emptyStateTextView.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
