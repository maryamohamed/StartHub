package com.training.starthub.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.starthub.R
import com.training.starthub.databinding.FragmentAllProductsBinding
import com.training.starthub.databinding.FragmentHomeBinding
import com.training.starthub.databinding.FragmentReviewsBinding
import com.training.starthub.ui.home.NewestAdapter
import com.training.starthub.ui.home.SpecialOffersAdapter


class ReviewsFragment : Fragment() {

    private var _binding: FragmentReviewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using view binding
        _binding = FragmentReviewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up LayoutManager
        binding.reviewsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Set the adapter
        binding.reviewsRecyclerView.adapter = ReviewsAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}