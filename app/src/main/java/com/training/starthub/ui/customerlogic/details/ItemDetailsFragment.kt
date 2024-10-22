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

class ItemDetailsFragment(val sheredPosition: String) : Fragment() {
    private var _binding: FragmentItemDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ItemDetailsViewModel by viewModels()
    private var finalPosition = 0
    private var productId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isNumericDouble(sheredPosition)) {
            finalPosition = sheredPosition.toInt()
        }
        else{
            productId = sheredPosition
        }

//        finalPosition = sheredPosition.toInt()

        Log.d("ItemDetailsFragment", "Final Received position: $finalPosition")

        if (productId.isNotEmpty()) {
            viewModel.fetchProductDetails(productId)
            viewModel.productDetails.observe(viewLifecycleOwner) { productDetails ->
                if (productDetails != null) {
                    displayProductDetails(productDetails)
                } else {
                    Log.e("ItemDetailsFragment", "Product details are null")
                    Toast.makeText(requireContext(), "Product details not found", Toast.LENGTH_SHORT).show()
                }

                viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }else{
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
                    Log.e("ItemDetailsFragment", "Invalid position: $sheredPosition")
                    Toast.makeText(requireContext(), "Invalid product position", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun displayProductDetails(productDetails: CustomerProduct) {
        binding.detailsContent.text = productDetails.description
        binding.detailsContent.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.clearProductDetails()
    }

    fun isNumericDouble(str: String): Boolean {
        return str.toDoubleOrNull() != null
    }
}
