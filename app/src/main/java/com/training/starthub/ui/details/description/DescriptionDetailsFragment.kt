package com.training.starthub.ui.details.description

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.training.starthub.R
import com.training.starthub.data.local.CustomerProduct
import com.training.starthub.databinding.FragmentDescriptionDetailsBinding


class DescriptionDetailsFragment : Fragment() {
    private var _binding: FragmentDescriptionDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DescriptionDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View? {
        _binding = FragmentDescriptionDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//
        val position = arguments?.getString("position") ?: "0"

        var finalPosition = position.toInt()

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
        binding.detailsContent.text = productDetails.description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}