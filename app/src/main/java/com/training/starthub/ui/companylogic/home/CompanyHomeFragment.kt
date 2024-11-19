package com.training.starthub.ui.companylogic.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.starthub.R
import com.training.starthub.databinding.FragmentCompanyHomeBinding
import com.training.starthub.ui.adapter.ProductsAdapter
import com.training.starthub.ui.model.Product

class CompanyHomeFragment : Fragment() {

    private lateinit var binding: FragmentCompanyHomeBinding

    private val productViewModel: HomeProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompanyHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel.loadUserProducts()

        productViewModel.products.observe(viewLifecycleOwner) { productList ->
            displayProducts(productList)
        }

        productViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        binding.addProduct.setOnClickListener {
            findNavController().navigate(R.id.action_CompanyHomeFragment_to_CompanyAddProductFragment)
        }

        binding.profile.setOnClickListener {
            findNavController().navigate(R.id.action_CompanyHomeFragment_to_CompanyProfileFragment)
        }
    }

    private fun displayProducts(products: List<Product>) {
        val adapter = ProductsAdapter(products)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

}