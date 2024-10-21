package com.training.starthub.ui.details



import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.training.starthub.ui.details.description.DescriptionDetailsFragment

class ProductDetailsViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DescriptionDetailsFragment()
            1 -> ReviewsFragment()
            2 -> RateProductFragment()
            else -> DescriptionDetailsFragment()
        }
    }
}
