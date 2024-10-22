package com.training.starthub.ui.investorlogic.home

import com.training.starthub.ui.adapter.CompanyAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.starthub.R
import com.training.starthub.databinding.FragmentHomeBinding
import com.training.starthub.ui.adapter.AllCompanyAdapter
import com.training.starthub.ui.customerlogic.allcompany.AllCompanyViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var allCompanyAdapter: AllCompanyAdapter

    // Initialize ViewModel
    private val viewModel: AllCompanyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using data binding
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Setup RecyclerView with LinearLayoutManager
        binding.companyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        allCompanyAdapter = AllCompanyAdapter(mutableListOf())
        binding.companyRecyclerView.adapter = allCompanyAdapter

        // Set click listener for the back button
//        binding.backButton.setOnClickListener {
//            requireActivity().onBackPressed()
//        }

        // Observe the companies LiveData from ViewModel
        viewModel.companies.observe(viewLifecycleOwner, Observer { companiesList ->
            allCompanyAdapter.updateData(companiesList)
        })

        // Fetch companies from Firestore
        viewModel.fetchCompaniesFromFirestore()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigationBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_search -> {
                    findNavController().navigate(R.id.action_HomeFragment_to_SearchFragment)
                    true
                }
                R.id.navigation_profile -> {
                    findNavController().navigate(R.id.action_HomeFragment_to_InvestorProfileFragment)
                    true
                }
                else -> false
            }
        }

    }

}




