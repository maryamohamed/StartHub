package com.training.starthub.ui.customerlogic.details

import com.training.starthub.databinding.FragmentCustomerDetailsBinding
import com.training.starthub.ui.adapter.ProductDetailsViewPagerAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.R
import com.training.starthub.ui.model.CustomerProduct

class CustomerDetailsFragment : Fragment() {

    private var _binding: FragmentCustomerDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var db : FirebaseFirestore
    private val viewModel: ItemDetailsViewModel by viewModels()
    private var finalPosition = 0
    private var productId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        db = FirebaseFirestore.getInstance()
        _binding = FragmentCustomerDetailsBinding.inflate(inflater, container, false)
        handleBackArrowClick()
        setupViewPagerAndTabs()
        return binding.root
    }

    private fun setupViewPagerAndTabs() {
        val viewPager: ViewPager2 = binding.viewPager
        val tabLayout: TabLayout = binding.tabLayout

        // Retrieve the position from the args or set it manually for testing
        val args = CustomerDetailsFragmentArgs.fromBundle(requireArguments())
        val position = args.position


        if (isNumericDouble(position)) {
            finalPosition = position.toInt()
        }
        else{
            productId = position
        }

        Log.d("CustomerDetailsFragment", "Received position: $position")


        val adapter = ProductDetailsViewPagerAdapter(this, position)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Details"
                1 -> tab.text = "Reviews"
                2 -> tab.text = "Rate Product"
            }
        }.attach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (productId.isNotEmpty()) {
            viewModel.fetchProductDetails(productId)
            viewModel.productDetails.observe(viewLifecycleOwner) { productDetails ->
                if (productDetails != null) {
                    displayProductDetails(productDetails)
                } else {
                    Log.e("ItemDetailsFragment", "Product details are null")
                }
                viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
        else{
            viewModel.getIds()
            viewModel.listOfIds.observe(viewLifecycleOwner) { mapOfIds ->
                val idList = mapOfIds.entries.toList()
                if (finalPosition in idList.indices) {
                    val selectedEntry = idList[finalPosition]
                    productId = selectedEntry.value
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
                    Log.e("ItemDetailsFragment", "Invalid position: $finalPosition")
                    Toast.makeText(requireContext(), "Invalid product position", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.detailsFavorites.setOnClickListener {
            binding.detailsFavorites.setImageResource(R.drawable.favorite_solid)
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            if (userId != null) {
                viewModel.setFavorite(productId, userId)
            }
        }


    }

    private fun handleBackArrowClick() {
        binding.backArrow.setOnClickListener {
            findNavController().navigate(R.id.action_productDetailsFragment_to_navigation_home)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun displayProductDetails(productDetails: CustomerProduct) {
        binding.detailsProductName.text = productDetails.name
        binding.detailsCompanyName.text = productDetails.CompanyName
        binding.detailsProductPrice.text = productDetails.price.toString()
        binding.detailsProductCategory.text = productDetails.category
        Glide.with(requireContext())
            .load(productDetails.imageUrl)
            .into(binding.productImage)
        binding.productImage.visibility = View.VISIBLE
        binding.detailsCompanyName.visibility = View.VISIBLE
        binding.detailsProductCategory.visibility = View.VISIBLE
        binding.detailsProductPrice.visibility = View.VISIBLE
        binding.detailsProductName.visibility = View.VISIBLE
    }

    fun isNumericDouble(str: String): Boolean {
        return str.toDoubleOrNull() != null
    }

}