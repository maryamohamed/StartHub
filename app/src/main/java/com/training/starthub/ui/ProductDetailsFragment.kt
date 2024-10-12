package com.training.starthub.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.training.starthub.R
import com.training.starthub.databinding.FragmentProductDetailsBinding

class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)

        setupViewPagerAndTabs()

        return binding.root
    }

    private fun setupViewPagerAndTabs() {
        val viewPager: ViewPager2 = binding.viewPager
        val tabLayout: TabLayout = binding.tabLayout

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
