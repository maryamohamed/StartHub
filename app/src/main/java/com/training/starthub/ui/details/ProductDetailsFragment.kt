package com.training.starthub.ui.details

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
import com.training.starthub.databinding.FragmentProductDetailsBinding

class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var db : FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        db = FirebaseFirestore.getInstance()
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        handleBackArrowClick()
        setupViewPagerAndTabs()
        return binding.root
    }


    private fun setupViewPagerAndTabs() {
        val viewPager: ViewPager2 = binding.viewPager
        val tabLayout: TabLayout = binding.tabLayout

        // Retrieve the position from the args or set it manually for testing
        val args = ProductDetailsFragmentArgs.fromBundle(requireArguments())
        val position = args.position

        Log.d("ProductDetailsFragment", "Received position: $position")

        val adapter = ProductDetailsViewPagerAdapter(this)
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

        val args = ProductDetailsFragmentArgs.fromBundle(requireArguments())
        val position = args.position.toInt()

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
