package com.training.starthub.ui.company.sales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.training.starthub.R
import com.training.starthub.databinding.FragmentCompanySalesBinding
import com.training.starthub.ui.adapter.SalesAdapter

class CompanySalesFragment : Fragment() {

    private lateinit var productSalesViewModel: ProductSalesViewModel
    private lateinit var binding: FragmentCompanySalesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanySalesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productSalesViewModel = ViewModelProvider(this)[ProductSalesViewModel::class.java]

        binding.productGrid.adapter = SalesAdapter(requireContext(), mutableListOf())

        productSalesViewModel.products.observe(viewLifecycleOwner) { products ->
            (binding.productGrid.adapter as SalesAdapter).clearProducts()
            (binding.productGrid.adapter as SalesAdapter).addAll(products)
            (binding.productGrid.adapter as SalesAdapter).notifyDataSetChanged()
        }

        productSalesViewModel.fetchUserProducts("userId")

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_CompanySalesFragment_to_CompanyProfileFragment)
        }
    }
}