package com.training.starthub.ui.customerlogic.review

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.starthub.databinding.FragmentCustomerReviewProductBinding
import com.training.starthub.ui.adapter.ReviewsAdapter

class CustomerReviewProductFragment(val sheredPosition: String) : Fragment() {
    private var _binding: FragmentCustomerReviewProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var reviewsAdapter: ReviewsAdapter
    private val viewModel: CustomerReviewViewModel by viewModels()
    private var finalPosition = 0
    private var productId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using view binding
        _binding = FragmentCustomerReviewProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isNumericDouble(sheredPosition)) {
            finalPosition = sheredPosition.toInt()
        }
        else{
            productId = sheredPosition
            Log.d("CustomerReviewFragment", "Received Position: $productId")
        }

//        val finalPosition = sheredPosition.toInt()
        Log.d("CustomerReviewFragment", "Received Position: $finalPosition")

        setupRecyclerView()
        observeViewModel(finalPosition, productId)
    }

    private fun setupRecyclerView() {
        // Set up LayoutManager and Adapter
        reviewsAdapter = ReviewsAdapter(mutableListOf())
        binding.reviewsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reviewsAdapter
        }
    }

    private fun observeViewModel(position: Int, productId: String) {

        if (productId.isNotEmpty()) {
            viewModel.fetchReviews(productId)
            viewModel.products.observe(viewLifecycleOwner) { productList ->
                reviewsAdapter.setData(productList)
            }
            viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
                Toast.makeText(requireContext(), "Error: $errorMsg", Toast.LENGTH_SHORT).show()
                Log.e("CustomerReviewFragment", "Error: $errorMsg")
            }
        } else {
            viewModel.getIds()
            viewModel.listOfIds.observe(viewLifecycleOwner) { mapOfIds ->
                val idList = mapOfIds.entries.toList()
                if (position in idList.indices) {
                    val productId = idList[position].value
                    Log.d("CustomerReviewFragment", "Product ID: $productId")
                    viewModel.fetchReviews(productId)
                } else {
                    Log.e("CustomerReviewFragment", "Invalid position: $position")
                    Toast.makeText(requireContext(), "Invalid product position", Toast.LENGTH_SHORT).show()
                }
            }

            viewModel.products.observe(viewLifecycleOwner) { productList ->
                reviewsAdapter.setData(productList)
            }

            viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
                Toast.makeText(requireContext(), "Error: $errorMsg", Toast.LENGTH_SHORT).show()
                Log.e("CustomerReviewFragment", "Error: $errorMsg")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun isNumericDouble(str: String): Boolean {
        return str.toDoubleOrNull() != null
    }
}
