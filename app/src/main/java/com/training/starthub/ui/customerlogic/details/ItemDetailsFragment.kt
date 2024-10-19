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
import com.training.starthub.ui.customerlogic.home.CustomerHomeViewModel

class ItemDetailsFragment : Fragment() {
    private var _binding: FragmentItemDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ItemDetailsViewModel by viewModels()
    private val cusViewModel: CustomerHomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getIds()
        viewModel.listOfIds.observe(viewLifecycleOwner) { listOfIds ->
            Log.d("ItemDetailsFragment", "Loaded products: $listOfIds")
        }
        var productId : String? = null
        val bundle = arguments
        val position = bundle?.getInt("position")
        Log.d("ItemDetailsFragment", "Position: $position")
        viewModel.listOfIds.observe(viewLifecycleOwner) { listOfIds ->
            productId = listOfIds[position?.toInt()].toString()
        }

        if (productId != null) {
            Log.d("ItemDetailsFragment", "Product ID: $productId")
            viewModel.fetchProductDetails(cusViewModel.setPosition.value.toString())
            viewModel.productDetails.observe(viewLifecycleOwner) { productDetails ->
                productDetails?.let {
                    binding.newestProductName.text = it.name
                    binding.newestProductCompany.text = it.company
                    binding.newestPrice.text = it.price.toString()
                    binding.newestProductCategory.text = it.category
                    binding.productDesc.text = it.description
                    Glide.with(requireContext())
                        .load(it.imageUrl)
                        .into(binding.newestProductImage)
                }
            }
        } else {
            Log.e("ItemDetailsFragment", "Product ID is null")
            Toast.makeText(requireContext(), "Product ID is null", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
