package com.training.starthub.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.training.starthub.ui.customerlogic.rate.RateProductFragment
import com.training.starthub.ui.customerlogic.details.ItemDetailsFragment
import com.training.starthub.ui.customerlogic.review.CustomerReviewProductFragment

class ProductDetailsViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ItemDetailsFragment()
            1 -> CustomerReviewProductFragment()
            2 -> RateProductFragment()
            else -> ItemDetailsFragment()
        }
    }

}