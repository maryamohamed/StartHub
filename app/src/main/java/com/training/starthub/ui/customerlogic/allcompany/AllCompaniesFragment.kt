package com.training.starthub.ui.customerlogic.allcompany

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.starthub.databinding.FragmentAllCompaniesBinding
import com.training.starthub.ui.adapter.AllCompanyAdapter
import com.training.starthub.ui.investorlogic.home.HomeFragmentDirections

class AllCompaniesFragment : Fragment() {

    private lateinit var binding: FragmentAllCompaniesBinding
    private lateinit var allCompanyAdapter: AllCompanyAdapter

    // Initialize ViewModel
    private val viewModel: AllCompanyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using data binding
        binding = FragmentAllCompaniesBinding.inflate(inflater, container, false)

        // Setup RecyclerView with LinearLayoutManager
        binding.companiesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        allCompanyAdapter = AllCompanyAdapter(mutableListOf()){ companyId ->
            navigateToCompanyDetails(companyId.toString())
        }
        binding.companiesRecyclerView.adapter = allCompanyAdapter

        // Set click listener for the back button
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        // Observe the companies LiveData from ViewModel
        viewModel.companies.observe(viewLifecycleOwner, Observer { companiesList ->
            allCompanyAdapter.updateData(companiesList)
        })

        // Fetch companies from Firestore
        viewModel.fetchCompaniesFromFirestore()

        return binding.root
    }

    fun navigateToCompanyDetails(companyId: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToCompanyDetailsFragment(companyId)
        findNavController().navigate(action)
    }
}