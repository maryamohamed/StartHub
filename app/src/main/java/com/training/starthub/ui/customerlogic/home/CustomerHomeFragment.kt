package com.training.starthub.ui.customerlogic.home

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
import com.google.firebase.auth.FirebaseAuth
import com.training.starthub.databinding.FragmentCustomerHomeBinding
import com.training.starthub.ui.adapter.NewestAdapter
import com.training.starthub.ui.adapter.SpecialOffersAdapter


class CustomerHomeFragment : Fragment() {
    private var _binding: FragmentCustomerHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: CustomerHomeViewModel by viewModels()
    private lateinit var newestAdapter: NewestAdapter
    private lateinit var specialOffersAdapter: SpecialOffersAdapter
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.loadProducts()
        newestAdapter = NewestAdapter(mutableListOf() ) { position ->
            navigateToProductDetails(position)
        }
        specialOffersAdapter = SpecialOffersAdapter { position ->
            navigateToProductDetails(position)
        }
        binding.recyclerViewSpecialOffer.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = specialOffersAdapter
        }


        homeViewModel.fetchUserName(auth.currentUser!!.uid)
        homeViewModel.userData.observe(viewLifecycleOwner) { data ->
            binding.homeCustomerName.text = data["name"]
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


        binding.recyclerViewVewest.setOnClickListener {
            Log.d("CustomerHomeFragment", "Item clicked ${it.tag as Int}")
            navigateToProductDetails(it.tag as Int)
        }

    }

    private fun navigateToProductDetails(position: Int) {
        Log.d("CustomerHomeFragment", "Navigating to product details for position: $position")

        val action = CustomerHomeFragmentDirections.actionNavigationHomeToProductDetailsFragment(position.toString())
        findNavController().navigate(action)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
