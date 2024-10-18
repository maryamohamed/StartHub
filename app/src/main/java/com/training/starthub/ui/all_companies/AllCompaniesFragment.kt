package com.training.starthub.ui.all_companies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.starthub.databinding.FragmentAllCompaniesBinding
import com.training.starthub.ui.adapter.CompanyAdapter

class AllCompaniesFragment : Fragment() {

    private lateinit var binding: FragmentAllCompaniesBinding
    private lateinit var companyAdapter: CompanyAdapter

    // Initialize ViewModel
    private val viewModel: CompanyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using data binding
        binding = FragmentAllCompaniesBinding.inflate(inflater, container, false)

        // Setup RecyclerView with LinearLayoutManager
        binding.companiesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        companyAdapter = CompanyAdapter(mutableListOf())
        binding.companiesRecyclerView.adapter = companyAdapter

        // Set click listener for the back button
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        // Observe the companies LiveData from ViewModel
        viewModel.companies.observe(viewLifecycleOwner, Observer { companiesList ->
            companyAdapter.updateData(companiesList)
        })

        // Fetch companies from Firestore
        viewModel.fetchCompaniesFromFirestore()

        return binding.root
    }
}
