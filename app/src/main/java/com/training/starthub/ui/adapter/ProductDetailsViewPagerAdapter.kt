package com.training.starthub.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.training.starthub.ui.customerlogic.details.CustomerDetailsFragmentDirections
import com.training.starthub.ui.customerlogic.rate.RateProductFragment
import com.training.starthub.ui.customerlogic.details.ItemDetailsFragment
import com.training.starthub.ui.customerlogic.review.CustomerReviewProductFragment

class ProductDetailsViewPagerAdapter(
    var fragment: Fragment,
    private val sheredPosition: String) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val itemDetailsFragment = ItemDetailsFragment()
                // Pass the position as an argument using a Bundle
                val args = Bundle().apply {
                    putString("position", sheredPosition)
                }
                itemDetailsFragment.arguments = args
                ItemDetailsFragment()
            }
            1 -> CustomerReviewProductFragment()
            2 -> RateProductFragment()
            else -> ItemDetailsFragment()
        }
    }

}