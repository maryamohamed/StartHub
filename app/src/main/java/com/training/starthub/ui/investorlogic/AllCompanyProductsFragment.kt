package com.training.starthub.ui.investorlogic

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.starthub.R
import com.training.starthub.databinding.FragmentAllCompanyProductsBinding
import com.training.starthub.ui.adapter.ProductsAdapter
import com.training.starthub.ui.adapter.ReviewsAdapter
import com.training.starthub.ui.companylogic.home.HomeProductViewModel
import com.training.starthub.ui.investorlogic.home.details.CompanyDetailsItemViewModel
import com.training.starthub.ui.model.Product


class AllCompanyProductsFragment(private val sheredPosition: String) : Fragment() {
    private val viewModel: HomeProductViewModel by viewModels()
    private val companyViewModel: CompanyDetailsItemViewModel by viewModels()
    private lateinit var reviewsAdapter: ReviewsAdapter
    private lateinit var binding: FragmentAllCompanyProductsBinding
    private var finalPosition = 0
    private var productId = ""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllCompanyProductsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isNumericDouble(sheredPosition)) {
            finalPosition = sheredPosition.toInt()
        }
        else {
            productId = sheredPosition
        }

        Log.d("AllCompanyProductsFragment", "Final Received position: $finalPosition")

        if (productId.isNotEmpty()) {
            viewModel.loadUserProducts(productId)
            viewModel.products.observe(viewLifecycleOwner) { productDetails ->
                if (productDetails != null) {
                    displayProducts(productDetails)
                } else {
                    Log.e("AllCompanyProductsFragment", "Product details are null")
                    Toast.makeText(requireContext(), "Product details not found", Toast.LENGTH_SHORT).show()
                }

                viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            companyViewModel.getCompaniesIds()
            companyViewModel.listOfIds.observe(viewLifecycleOwner) { mapOfIds ->
                val idList = mapOfIds.entries.toList()
                if (finalPosition in idList.indices) {
                    val selectedEntry = idList[finalPosition]
                    val productId = selectedEntry.value
                    Log.d("AllCompanyProductsFragment", "Product ID: $productId")

                    viewModel.loadUserProducts(productId)
                    viewModel.products.observe(viewLifecycleOwner) { productDetails ->
                        if (productDetails != null) {
                            Log.d("AllCompanyProductsFragment", "Product Details: $productDetails")
                            displayProducts(productDetails)
                        } else {
                            Log.e("AllCompanyProductsFragment", "Product details are null")
                            Toast.makeText(requireContext(), "Product details not found", Toast.LENGTH_SHORT).show()
                        }
                    }
                    viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("AllCompanyProductsFragment", "Invalid position: $sheredPosition")
                    Toast.makeText(requireContext(), "Invalid product position", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun isNumericDouble(str: String): Boolean {
        return str.toDoubleOrNull() != null
    }

    private fun displayProducts(products: List<Product>) {
        val adapter = ProductsAdapter(products)
        binding.companiesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.companiesRecyclerView.adapter = adapter
    }





}