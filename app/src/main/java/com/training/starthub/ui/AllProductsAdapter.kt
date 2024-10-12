package com.training.starthub.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.training.starthub.R

class AllProductsAdapter(private val onItemClicked: (Int) -> Unit) : RecyclerView.Adapter<AllProductsAdapter.AllProductsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllProductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return AllProductsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllProductsViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClicked(position)  // Trigger click event
        }    }

    override fun getItemCount(): Int {
        return 30
    }

    class AllProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}
