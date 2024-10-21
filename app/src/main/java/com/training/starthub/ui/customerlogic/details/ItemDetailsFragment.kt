package com.training.starthub.ui.customerlogic.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.training.starthub.databinding.FragmentItemDetailsBinding
import com.training.starthub.ui.model.CustomerProduct

class ItemDetailsFragment : Fragment() {
    private var _binding: FragmentItemDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ItemDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val position = arguments?.getString("position") ?: "0"

        var finalPosition = position.toInt()
        Log.d("ItemDetailsFragment", "Final Received position: $finalPosition")

        viewModel.getIds()
        viewModel.listOfIds.observe(viewLifecycleOwner) { mapOfIds ->
            val idList = mapOfIds.entries.toList()
            if (finalPosition in idList.indices) {
                val selectedEntry = idList[finalPosition]
                val productId = selectedEntry.value
                Log.d("ItemDetailsFragment", "Product ID: $productId")

                viewModel.fetchProductDetails(productId)
                viewModel.productDetails.observe(viewLifecycleOwner) { productDetails ->
                    if (productDetails != null) {
                        displayProductDetails(productDetails)
                    } else {
                        Log.e("ItemDetailsFragment", "Product details are null")
                        Toast.makeText(requireContext(), "Product details not found", Toast.LENGTH_SHORT).show()
                    }
                }
                viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.e("ItemDetailsFragment", "Invalid position: $position")
                Toast.makeText(requireContext(), "Invalid product position", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayProductDetails(productDetails: CustomerProduct) {
        binding.newestProductName.text = productDetails.name
        binding.newestProductCompany.text = productDetails.CompanyName
        binding.newestPrice.text = productDetails.price.toString()
        binding.newestProductCategory.text = productDetails.category
        binding.productDesc.text = productDetails.description
        Glide.with(requireContext())
            .load(productDetails.imageUrl)
            .into(binding.newestProductImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.clearProductDetails()
    }
}
