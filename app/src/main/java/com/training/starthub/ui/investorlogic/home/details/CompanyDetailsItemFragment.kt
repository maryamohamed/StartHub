package com.training.starthub.ui.investorlogic.home.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.training.starthub.R
import com.training.starthub.databinding.FragmentCompanyDetailsItemBinding
import com.training.starthub.ui.model.Company


class CompanyDetailsItemFragment(private val sheredPosition: String) : Fragment() {
    private val viewModel: CompanyDetailsItemViewModel by viewModels()
    private lateinit var binding: FragmentCompanyDetailsItemBinding
    private var finalPosition = 0
    private var productId = ""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompanyDetailsItemBinding.inflate(inflater,container,false)
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

        Log.d("ItemDetailsFragment", "Final Received position: $finalPosition")

        if (productId.isNotEmpty()) {
            viewModel.fetchCompanyDetails(productId)
            viewModel.companyDetails.observe(viewLifecycleOwner) { productDetails ->
                if (productDetails != null) {
                    displayCompanyDetails(productDetails)
                } else {
                    Log.e("ItemDetailsFragment", "Product details are null")
                    Toast.makeText(requireContext(), "Product details not found", Toast.LENGTH_SHORT).show()
                }
                viewModel.fetchCompanyInfo(productId)
                viewModel.companyInfo.observe(viewLifecycleOwner) { companyInfo ->
                    if (companyInfo != null) {
                        displayCompanyInfo(companyInfo)
                    } else {
                        Log.e("ItemDetailsFragment", "Company info is null")
                        Toast.makeText(requireContext(), "Company info not found", Toast.LENGTH_SHORT).show()
                    }
                }

                viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            viewModel.getCompaniesIds()
            viewModel.listOfIds.observe(viewLifecycleOwner) { mapOfIds ->
                val idList = mapOfIds.entries.toList()
                if (finalPosition in idList.indices) {
                    val selectedEntry = idList[finalPosition]
                    val productId = selectedEntry.value
                    Log.d("ItemDetailsFragment", "Product ID: $productId")

                    viewModel.fetchCompanyDetails(productId)
                    viewModel.companyDetails.observe(viewLifecycleOwner) { productDetails ->
                        if (productDetails != null) {
                            displayCompanyDetails(productDetails)
                        } else {
                            Log.e("ItemDetailsFragment", "Product details are null")
                            Toast.makeText(requireContext(), "Product details not found", Toast.LENGTH_SHORT).show()
                        }
                    }
                    viewModel.fetchCompanyInfo(productId)
                    viewModel.companyInfo.observe(viewLifecycleOwner) { companyInfo ->
                        if (companyInfo != null) {
                            displayCompanyInfo(companyInfo)
                            } else {
                            Log.e("ItemDetailsFragment", "Company info is null")
                            Toast.makeText(requireContext(), "Company info not found", Toast.LENGTH_SHORT).show()
                        }
                    }

                    viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("ItemDetailsFragment", "Invalid position: $sheredPosition")
                    Toast.makeText(requireContext(), "Invalid product position", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun isNumericDouble(str: String): Boolean {
        return str.toDoubleOrNull() != null
    }

    private fun displayCompanyDetails(companyDetails: Company) {
        binding.detailsContent.text = companyDetails.description
        binding.detailsContent.visibility = View.VISIBLE
        binding.detailsData.text = companyDetails.dateOfCreation
    }

    private fun displayCompanyInfo(companyInfo: Company) {
        binding.detailsNumber.text = companyInfo.phone
        binding.detailsEmail.text = companyInfo.email
    }



}