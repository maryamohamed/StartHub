package com.training.starthub.ui.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.training.starthub.ui.investorlogic.AllCompanyProductsFragment

import com.training.starthub.ui.investorlogic.home.details.CompanyDetailsItemFragment
import com.training.starthub.ui.investorlogic.CompanyRateFragment

class CompanyDetailsViewPagerAdapter(
    var fragment: Fragment,
    private val sheredPosition: String) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        Log.d("ProductDetailsViewPagerAdapter", "Received position: $sheredPosition")
        return when (position) {
            0 -> CompanyDetailsItemFragment(sheredPosition)
            1 -> AllCompanyProductsFragment(sheredPosition)
            2 -> CompanyRateFragment(sheredPosition)
            else -> CompanyDetailsItemFragment(sheredPosition)
        }
    }
}