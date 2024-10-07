package com.training.starthub.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.training.starthub.R

fun Fragment.declareViewPager(currentItem: Int) {
    val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
    viewPager?.currentItem = currentItem
}
fun Int.toDollarFormat() = "$this $ "

fun Fragment.setLinearLayoutRecyclerView(recyclerView: RecyclerView?) {
    recyclerView?.layoutManager = LinearLayoutManager(
        activity,
        LinearLayoutManager.VERTICAL,
        false
    )
}
fun Fragment.showToast(message: Any?) {
    Toast.makeText(requireContext(),"$message", Toast.LENGTH_SHORT).show()
}
