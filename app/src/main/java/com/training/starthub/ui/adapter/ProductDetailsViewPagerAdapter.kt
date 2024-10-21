package com.training.starthub.ui.adapter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.training.starthub.ui.customerlogic.rate.RateProductFragment
import com.training.starthub.ui.customerlogic.details.ItemDetailsFragment
import com.training.starthub.ui.customerlogic.review.CustomerReviewProductFragment

class ProductDetailsViewPagerAdapter(
    var fragment: Fragment,
    private val sheredPosition: String) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        Log.d("ProductDetailsViewPagerAdapter", "Received position: $sheredPosition")
        return when (position) {
            0 -> {
                ItemDetailsFragment().apply {
                // Pass the position as an argument using a Bundle
                     arguments = Bundle().apply {
                        putString("position", sheredPosition)
                    }
                }
                ItemDetailsFragment()
            }
            1 -> CustomerReviewProductFragment()
            2 -> RateProductFragment()
            else -> ItemDetailsFragment()
        }
    }

}