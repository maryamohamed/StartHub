package com.training.starthub.ui.investorlogic

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.starthub.R
import com.training.starthub.databinding.FragmentSearchBinding
import com.training.starthub.ui.model.Company
import com.training.starthub.ui.adapter.InvestorSearchAdapter
import com.training.starthub.ui.customerlogic.allcompany.AllCompanyViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: InvestorSearchAdapter
    private val viewModel: AllCompanyViewModel by viewModels()

    private var companyList: List<Company> = listOf()
    private var filteredList: MutableList<Company> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.bottomNavigationBar.selectedItemId = R.id.navigation_search
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = InvestorSearchAdapter(this.requireContext(), filteredList)
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.searchRecyclerView.adapter = adapter


        binding.searchRecyclerView.visibility = View.GONE


        viewModel.companies.observe(viewLifecycleOwner) { companies ->
            Log.d("SearchFragment", "Companies loaded: ${companies.size}")
            companyList = companies
            filteredList.clear()
            filteredList.addAll(companyList)
            adapter.notifyDataSetChanged()
        }

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

        viewModel.fetchCompaniesFromFirestore()
    }

    private fun filterCompanies(query: String?) {
        filteredList.clear()
        if (query.isNullOrEmpty()) {
            binding.searchRecyclerView.visibility = View.GONE
        } else {
            filteredList.addAll(companyList.filter {
                it.name.contains(query, true) || it.category.contains(query, true)
            })
            if (filteredList.isNotEmpty()) {
                binding.searchRecyclerView.visibility = View.VISIBLE
            } else {
                binding.searchRecyclerView.visibility = View.GONE
            }
        }
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
