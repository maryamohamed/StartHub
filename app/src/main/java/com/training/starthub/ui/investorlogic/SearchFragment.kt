package com.training.starthub.ui.investorlogic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.starthub.R
import com.training.starthub.databinding.FragmentSearchBinding
import com.training.starthub.ui.adapter.Company
import com.training.starthub.ui.adapter.InvestorSearchAdapter

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: InvestorSearchAdapter
    private var companyList = listOf(
        Company(R.drawable.ic_launcher_background, "Rolex", "Luxury", "Watches"),
        Company(R.drawable.ic_launcher_background, "Apple", "Tech", "Electronics"),
        Company(R.drawable.ic_launcher_background, "McDonald's", "Food", "Restaurants"),
        Company(R.drawable.ic_launcher_background, "Intrax", "Marketing", "Advertising"),
        Company(R.drawable.ic_launcher_background, "Adidas", "Sports", "Shoes"),
    )
    private var filteredList = companyList.toMutableList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.bottomNavigationBar.selectedItemId = R.id.navigation_search

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super. onViewCreated(view, savedInstanceState)
        // Set up RecyclerView
        filteredList = companyList.toMutableList()

        // Set up RecyclerView
        adapter = InvestorSearchAdapter(this.requireContext(),filteredList)
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.searchRecyclerView.adapter = adapter

        // Setup search functionality
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterCompanies(newText)
                return true
            }
        })

        binding.bottomNavigationBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    findNavController().navigate(R.id.action_SearchFragment_to_HomeFragment)
                    true
                }
                else -> false
            }
        }

    }

    private fun filterCompanies(query: String?) {
        filteredList.clear()
        if (query.isNullOrEmpty()) {
            filteredList.addAll(companyList)
        } else {
            filteredList.addAll(companyList.filter {
                it.name.contains(query, true) || it.category.contains(query, true)
            })
        }
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
