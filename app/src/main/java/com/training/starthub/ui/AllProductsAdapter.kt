package com.training.starthub.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.training.starthub.R

class AllProductsAdapter : RecyclerView.Adapter<AllProductsAdapter.AllProductsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllProductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return AllProductsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllProductsViewHolder, position: Int) {
        // Bind your data here
    }

    override fun getItemCount(): Int {
        // Return the number of items
        return 30
    }

    class AllProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize your views (e.g., ImageView, TextView) here
    }
}
