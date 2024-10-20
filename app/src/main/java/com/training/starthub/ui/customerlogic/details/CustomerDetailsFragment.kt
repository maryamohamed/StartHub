package com.training.starthub.ui.customerlogic.details

import com.training.starthub.databinding.FragmentCustomerDetailsBinding
import com.training.starthub.ui.adapter.ProductDetailsViewPagerAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.FirebaseFirestore
import com.training.starthub.R

class CustomerDetailsFragment : Fragment() {

    private var _binding: FragmentCustomerDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var db : FirebaseFirestore

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

        val args = CustomerDetailsFragmentArgs.fromBundle(requireArguments())
        val position = args.position.toInt()

//        CustomerDetailsFragmentDirections.actionProductDetailsFragmentToItemDetailsFragment(position.toString())
//        findNavController().navigate(action)

////        binding.productImage.setImageResource()
//        binding.detailsFavorites.setOnClickListener {
//            val docRef = db.collection("All_Products")
//                .document().collection("secPage")
//                .document()
//
//            // Get the data from the document
//            val data = docRef.get()
//
//            val name = data.getString("name") ?: ""
//            val companyName = data.getString("name") ?: ""
//            val imageUrl = data.getString("imageUrl") ?: ""
//            val description = data.getString("description") ?: ""
//            val price = data.getString("price") ?: ""
//            val category = data.getString("category") ?: ""
//            val companyLogo = data.getString("CompanyLogo") ?: ""
//
////        val snapshot = docRef.get().await()
//            if (data.exists()) {
//                val companyLogo = data.getString("imageUrl") ?: ""
//
//                val productDetails = hashMapOf(
//                    "name" to name,
//                    "description" to description,
//                    "price" to price.toInt(),
//                    "category" to category,
//                    "imageUrl" to imageUrl,
//                    "CompanyLogo" to companyLogo,
//                    "CompanyName" to companyName
//                )
//            }
//        }
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
}