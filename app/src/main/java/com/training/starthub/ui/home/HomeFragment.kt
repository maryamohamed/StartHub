package com.training.starthub.ui.home

import Product
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.R
import com.training.starthub.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var newestAdapter: NewestAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newestAdapter = NewestAdapter(mutableListOf()) { position ->
            navigateToProductDetails(position)
        }

        binding.recyclerViewVewest.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newestAdapter
        }

        homeViewModel.products.observe(viewLifecycleOwner) { productList ->
            newestAdapter.setData(productList)
        }

        homeViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        homeViewModel.loadProducts()
    }

    private fun navigateToProductDetails(position: Int) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
